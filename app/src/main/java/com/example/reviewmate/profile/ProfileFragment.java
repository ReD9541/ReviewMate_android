package com.example.reviewmate.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.ProfileFragmentBinding;
import com.example.reviewmate.login.LoginFragment;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Review;
import com.example.reviewmate.model.Userinfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding binding;
    private SharedViewModel sharedViewModel;
    private ProfileViewModel profileViewModel;

    private ProfileMoviesAdapter moviesWatchedAdapter;
    private ProfileMoviesAdapter moviesWatchlistedAdapter;
    private ProfileReviewsAdapter reviewsAdapter;

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
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        populateUserInfo();
        setupRecyclerViews();

        sharedViewModel.getUserId().observe(getViewLifecycleOwner(), userId -> {
            if (userId != null && userId != -1) {
                profileViewModel.getUserInfo(userId).observe(getViewLifecycleOwner(), userinfo -> {
                    if (userinfo != null) {
                        updateAdditionalUserInfo(userinfo);
                        loadUserSpecificData(userId);
                    } else {
                        Toast.makeText(requireContext(), "User information not found", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(requireContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
            }
        });

        binding.logoutButton.setOnClickListener(v -> handleLogout());
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

    private void setupRecyclerViews() {
        // Movies Watched RecyclerView
        moviesWatchedAdapter = new ProfileMoviesAdapter();
        binding.moviesWatchedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.moviesWatchedRecyclerView.setAdapter(moviesWatchedAdapter);

        // Movies Watchlisted RecyclerView
        moviesWatchlistedAdapter = new ProfileMoviesAdapter();
        binding.watchlistRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.watchlistRecyclerView.setAdapter(moviesWatchlistedAdapter);

        // User Reviews RecyclerView
        reviewsAdapter = new ProfileReviewsAdapter();
        binding.userReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.userReviewsRecyclerView.setAdapter(reviewsAdapter);
    }

    private void loadUserSpecificData(int userId) {
        // Load Movies Watched
        profileViewModel.getMoviesWatched(userId).observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                moviesWatchedAdapter.submitList(movies);
            }
        });

        // Load Movies Watchlisted
        profileViewModel.getMoviesWatchlisted(userId).observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                moviesWatchlistedAdapter.submitList(movies);
            }
        });

        // Load User Reviews
        profileViewModel.getUserReviews(userId).observe(getViewLifecycleOwner(), reviews -> {
            if (reviews != null) {
                reviewsAdapter.submitList(reviews);
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
