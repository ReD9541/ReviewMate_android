package com.example.reviewmate.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.ProfileFragmentBinding;
import com.example.reviewmate.login.LoginFragment;
import com.example.reviewmate.model.Userinfo;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding binding;
    private SharedViewModel sharedViewModel;
    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Use ViewBinding to inflate the view
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Initialize ProfileViewModel
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Populate Profile Information from global variables
        populateUserInfo();

        // Observe userId from SharedViewModel and fetch userinfo data accordingly
        sharedViewModel.getUserId().observe(getViewLifecycleOwner(), userId -> {
            if (userId != null && userId != -1) {
                // Use ProfileViewModel to fetch additional user information
                profileViewModel.getUserInfo(userId).observe(getViewLifecycleOwner(), userinfo -> {
                    if (userinfo != null) {
                        updateAdditionalUserInfo(userinfo);
                    } else {
                        Toast.makeText(requireContext(), "User information not found", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(requireContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Populate the basic user info using global variables from the LoginFragment.
     */
    private void populateUserInfo() {
        if (LoginFragment.loggedInUserEmail != null && LoginFragment.loggedInUserName != null) {
            binding.profileName.setText(LoginFragment.loggedInUserName);
            binding.profileEmail.setText(LoginFragment.loggedInUserEmail);
        } else {
            binding.profileName.setText("Unknown User");
            binding.profileEmail.setText("No Email Provided");
        }

        // Load a placeholder profile picture if needed
        binding.profileImage.setImageResource(R.drawable.ic_profile_placeholder);
    }

    /**
     * Update additional user information from the Userinfo model.
     */
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
            // Load profile picture using Picasso
            Picasso.get().load(userinfo.getProfilePictureUrl()).resize(200, 200).centerCrop().into(binding.profileImage);
        } else {
            binding.profileImage.setImageResource(R.drawable.ic_profile_placeholder);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
