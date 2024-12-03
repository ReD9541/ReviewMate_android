package com.example.reviewmate.movie;

import android.os.Bundle;
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
                searchMovies(query, getSelectedGenre(), getSelectedCategory());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovies(newText, getSelectedGenre(), getSelectedCategory());
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
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                searchViewModel.getGenres()
        );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genreSpinner.setAdapter(genreAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                searchViewModel.getLanguages()
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(categoryAdapter);

        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchMovies(binding.searchView.getQuery().toString(), getSelectedGenre(), getSelectedCategory());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        binding.genreSpinner.setOnItemSelectedListener(filterListener);
        binding.categorySpinner.setOnItemSelectedListener(filterListener);
    }

    private void searchMovies(String query, String genre, String language) {
        searchViewModel.searchMovies(query, genre, language).observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                adapter.submitList(movies);
            } else {
                adapter.submitList(List.of());
            }
        });
    }

    private String getSelectedGenre() {
        Object selectedItem = binding.genreSpinner.getSelectedItem();
        return selectedItem != null ? selectedItem.toString() : "";
    }

    private String getSelectedCategory() {
        Object selectedItem = binding.categorySpinner.getSelectedItem();
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
