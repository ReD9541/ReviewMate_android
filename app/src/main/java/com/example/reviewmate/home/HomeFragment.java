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

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private MovieRecyclerViewAdapter topRatedAdapter;
    private MovieRecyclerViewAdapter latestAdapter;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.homeToolbar);
        ImageButton profileButton = toolbar.findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment)
        );

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerViews();

        // Observe the movies LiveData from ViewModel
        homeViewModel.getTopRatedMovies().observe(getViewLifecycleOwner(), movies -> {
            topRatedAdapter.submitList(movies);
        });

        homeViewModel.getLatestMovies().observe(getViewLifecycleOwner(), movies -> {
            latestAdapter.submitList(movies);
        });
    }

    private void setupRecyclerViews() {
        // Set up the adapters for the RecyclerViews
        topRatedAdapter = new MovieRecyclerViewAdapter(movie -> {
            // top-rated movie (
        });

        latestAdapter = new MovieRecyclerViewAdapter(movie -> {
            //  latest movie
        });

        binding.topRatedMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.topRatedMoviesRecyclerView.setAdapter(topRatedAdapter);

        binding.latestMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.latestMoviesRecyclerView.setAdapter(latestAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
