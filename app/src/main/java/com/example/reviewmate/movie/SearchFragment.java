package com.example.reviewmate.movie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.reviewmate.R;
import com.example.reviewmate.adapters.MovieRecyclerViewAdapter;
import com.example.reviewmate.databinding.SearchFragmentBinding;
import com.example.reviewmate.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchFragmentBinding binding;
    private SearchViewModel searchViewModel;
    private MovieRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        setupRecyclerView();
        setupFilters();

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query, getSelectedGenre(), getSelectedLanguage());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovies(newText, getSelectedGenre(), getSelectedLanguage());
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new MovieRecyclerViewAdapter(movie -> navigateToMovieDetail(movie));
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.searchRecyclerView.setAdapter(adapter);
    }

    private void setupFilters() {
        searchViewModel.getGenres().observe(getViewLifecycleOwner(), genres -> {
            List<String> genreOptions = new ArrayList<>();
            genreOptions.add("All");
            genreOptions.addAll(genres);

            ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genreOptions);
            genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.genreSpinner.setAdapter(genreAdapter);
        });

        searchViewModel.getLanguages().observe(getViewLifecycleOwner(), languages -> {
            List<String> languageOptions = new ArrayList<>();
            languageOptions.add("All");
            languageOptions.addAll(languages);

            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, languageOptions);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.languageSpinner.setAdapter(categoryAdapter);
        });

        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchMovies(binding.searchView.getQuery().toString(), getSelectedGenre(), getSelectedLanguage());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        binding.genreSpinner.setOnItemSelectedListener(filterListener);
        binding.languageSpinner.setOnItemSelectedListener(filterListener);
    }

    private void searchMovies(String query, String genre, String language) {
        if ("All".equals(genre)) genre = "";
        if ("All".equals(language)) language = "";

        Log.d("SearchFragment", "Searching with query: " + query + ", genre: " + genre + ", language: " + language);

        searchViewModel.searchMovies(query, genre, language).observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && !movies.isEmpty()) {
                Log.d("SearchFragment", "Movies found: " + movies.size());
                adapter.submitList(movies);
            } else {
                Log.d("SearchFragment", "No movies found");
                adapter.submitList(List.of());
            }
        });
    }


    private String getSelectedGenre() {
        Object selectedItem = binding.genreSpinner.getSelectedItem();
        return selectedItem != null ? selectedItem.toString() : "";
    }

    private String getSelectedLanguage() {
        Object selectedItem = binding.languageSpinner.getSelectedItem();
        return selectedItem != null ? selectedItem.toString() : "";
    }

    private void navigateToMovieDetail(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putInt("MOVIE_ID", movie.getMovieId());
        Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment2_to_movieDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
