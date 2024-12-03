package com.example.reviewmate.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.EditProfileFragmentBinding;
import com.example.reviewmate.model.Userinfo;

public class EditProfileFragment extends Fragment {

    private EditProfileFragmentBinding binding;
    private SharedViewModel viewModel;
    private Userinfo loggedInUser;

    public EditProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = EditProfileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        if (getArguments() != null) {
            loggedInUser = (Userinfo) getArguments().getSerializable("USER_DETAILS");
        }

        if (loggedInUser != null) {
            loadUserData(loggedInUser);
        } else {
            Toast.makeText(requireContext(), "Error: User data not available", Toast.LENGTH_SHORT).show();
        }

        binding.editProfileSaveButton.setOnClickListener(v -> saveProfileChanges());
        binding.updatePasswordButton.setOnClickListener(v -> updatePassword());
    }

    private void loadUserData(Userinfo user) {
        binding.editFirstNameEditText.setText(user.getFirstName());
        binding.editLastNameEditText.setText(user.getLastName());
        binding.editBioEditText.setText(user.getBio());
        binding.editAddressEditText.setText(user.getAddress());
        binding.profileEditImageView.setImageResource(R.drawable.ic_profile_placeholder);
    }

    private void saveProfileChanges() {
        if (loggedInUser == null) {
            Toast.makeText(requireContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String firstName = binding.editFirstNameEditText.getText().toString().trim();
        String lastName = binding.editLastNameEditText.getText().toString().trim();
        String bio = binding.editBioEditText.getText().toString().trim();
        String address = binding.editAddressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            Toast.makeText(requireContext(), "First name and last name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.updateUserProfile(loggedInUser.getUserId(), firstName, lastName, bio, address).observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                loggedInUser.setFirstName(firstName);
                loggedInUser.setLastName(lastName);
                loggedInUser.setBio(bio);
                loggedInUser.setAddress(address);
            } else {
                Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassword() {
        if (loggedInUser == null) {
            Toast.makeText(requireContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPassword = binding.editPasswordEditText.getText().toString().trim();
        String confirmPassword = binding.editConfirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(requireContext(), "Password fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.updateUserPassword(loggedInUser.getUserId(), newPassword).observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                binding.editPasswordEditText.setText("");
                binding.editConfirmPasswordEditText.setText("");
            } else {
                Toast.makeText(requireContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
