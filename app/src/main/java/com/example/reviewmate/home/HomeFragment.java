package com.example.reviewmate.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.HomeFragmentBinding;
import com.example.reviewmate.adapters.MovieRecyclerViewAdapter;
import com.example.reviewmate.model.Movie;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private MovieRecyclerViewAdapter topRatedAdapter;
    private MovieRecyclerViewAdapter latestAdapter;
    private HomeViewModel homeViewModel;

    //initializing the home fragment layout
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    //this class helps fill up info in the toolbar (my top text bar welcoming user), gets toprated movies, from the MovieRecyclerViewAdapter, and does the same fot the latest adapter
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar(view);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        setupRecyclerViews();

        homeViewModel.getTopRatedMovies().observe(getViewLifecycleOwner(), movies -> {
            topRatedAdapter.submitList(movies);
        });

        homeViewModel.getLatestMovies().observe(getViewLifecycleOwner(), movies -> {
            latestAdapter.submitList(movies);
        });
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.homeToolbar);
        ImageButton profileButton = toolbar.findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment)
        );
    }

    private void setupRecyclerViews() {
        // Set up Top Rated Movies RecyclerView
        topRatedAdapter = new MovieRecyclerViewAdapter(this::navigateToMovieDetail);
        binding.topRatedMoviesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.topRatedMoviesRecyclerView.setAdapter(topRatedAdapter);

        // Set up Latest Movies RecyclerView
        latestAdapter = new MovieRecyclerViewAdapter(this::navigateToMovieDetail);
        binding.latestMoviesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.latestMoviesRecyclerView.setAdapter(latestAdapter);
    }

    private void navigateToMovieDetail(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putInt("MOVIE_ID", movie.getMovieId());
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
