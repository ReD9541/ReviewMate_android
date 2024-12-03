package com.example.reviewmate.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.reviewmate.R;
import com.example.reviewmate.adapters.ProfileMoviesAdapter;
import com.example.reviewmate.adapters.ProfileReviewsAdapter;
import com.example.reviewmate.databinding.ProfileFragmentBinding;
import com.example.reviewmate.login.LoginFragment;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Userinfo;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding binding;
    private SharedViewModel sharedViewModel;
    private ProfileViewModel profileViewModel;

    private ProfileMoviesAdapter moviesWatchedAdapter;
    private ProfileMoviesAdapter moviesWatchlistedAdapter;
    private ProfileReviewsAdapter profileReviewsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        profileViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(ProfileViewModel.class);


        populateUserInfo();
        setupRecyclerViews();

        sharedViewModel.getUserId().observe(getViewLifecycleOwner(), userId -> {
            if (userId != null && userId != -1) {
                profileViewModel.getUserInfo(userId).observe(getViewLifecycleOwner(), userinfo -> {
                    if (userinfo != null) {
                        updateAdditionalUserInfo(userinfo);
                        loadUserSpecificData(userId);
                        observeMovieReviewsAndMovieNames(userId);
                    } else {
                        Toast.makeText(requireContext(), "User information not found", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(requireContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
            }
        });

        binding.logoutButton.setOnClickListener(v -> handleLogout());
        binding.editProfileButton.setOnClickListener(v -> sendToEditProfile());
    }

    private void sendToEditProfile() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("USER_DETAILS", sharedViewModel.getUserId().getValue());
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_editProfileFragment, bundle);
    }


    private void populateUserInfo() {
        if (LoginFragment.loggedInUserEmail != null && LoginFragment.loggedInUserName != null) {
            binding.profileName.setText(LoginFragment.loggedInUserName);
            binding.profileEmail.setText(LoginFragment.loggedInUserEmail);
        } else {
            binding.profileName.setText("Unknown User");
            binding.profileEmail.setText("No Email Provided");
        }

        binding.profileImage.setImageResource(R.drawable.ic_profile_placeholder);
    }

    private void updateAdditionalUserInfo(Userinfo userinfo) {
        if (userinfo.getFirstName() != null && userinfo.getLastName() != null) {
            binding.profileName.setText(userinfo.getFirstName() + " " + userinfo.getLastName());
        }

        if (userinfo.getCountry() != null) {
            binding.profileCountry.setText("Country: " + userinfo.getCountry());
        } else {
            binding.profileCountry.setText("Country: Not Available");
        }

        if (userinfo.getJoinedOn() != null) {
            binding.profileJoinedDate.setText("Joined on: " + userinfo.getJoinedOn());
        } else {
            binding.profileJoinedDate.setText("Joined on: Not Available");
        }

        if (userinfo.getBio() != null) {
            binding.profileBio.setText("Bio: " + userinfo.getBio());
        } else {
            binding.profileBio.setText("Bio: Not Available");
        }

        if (userinfo.getProfilePictureUrl() != null && !userinfo.getProfilePictureUrl().isEmpty()) {
            Picasso.get().load(userinfo.getProfilePictureUrl()).resize(200, 200).centerCrop().into(binding.profileImage);
        } else {
            binding.profileImage.setImageResource(R.drawable.ic_profile_placeholder);
        }
    }

    private void navigateToMovieDetail(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putInt("MOVIE_ID", movie.getMovieId());
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_movieDetailFragment, bundle);
    }

    private void setupRecyclerViews() {
        // Movies Watched RecyclerView
        moviesWatchedAdapter = new ProfileMoviesAdapter(movie -> navigateToMovieDetail(movie));
        binding.moviesWatchedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.moviesWatchedRecyclerView.setAdapter(moviesWatchedAdapter);

        // Movies Watchlisted RecyclerView
        moviesWatchlistedAdapter = new ProfileMoviesAdapter(movie -> navigateToMovieDetail(movie));
        binding.moviesWatchlistRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.moviesWatchlistRecyclerView.setAdapter(moviesWatchlistedAdapter);

        // User Reviews RecyclerView
        profileReviewsAdapter = new ProfileReviewsAdapter();
        binding.userReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.userReviewsRecyclerView.setAdapter(profileReviewsAdapter);
    }

    private void loadUserSpecificData(int userId) {
        profileViewModel.getMoviesWatched(userId).observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                moviesWatchedAdapter.submitList(movies);
            }
        });
        profileViewModel.getMoviesWatchlisted(userId).observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                moviesWatchlistedAdapter.submitList(movies);
            }
        });
    }

    private void observeMovieReviewsAndMovieNames(int userId) {
        profileViewModel.getUserReviewsWithMovieNamesByUserId(userId).observe(getViewLifecycleOwner(), reviews -> {
            if (reviews != null) {
                profileViewModel.getmovienamesbyUserId(userId).observe(getViewLifecycleOwner(), movieNames -> {
                    if (movieNames != null && movieNames.size() == reviews.size()) {
                        profileReviewsAdapter.submitList(reviews, movieNames);
                    }
                });
            }
        });
    }

    private void handleLogout() {
        sharedViewModel.clearUserId();
        NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_loginFragment);
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
