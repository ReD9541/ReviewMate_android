package com.example.reviewmate.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.RegisterFragmentBinding;
import com.example.reviewmate.model.User;
import com.example.reviewmate.model.Userinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private RegisterFragmentBinding binding;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.registerButtonBack.setOnClickListener(v -> {
            navigateBackToLogin();
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBackToLogin();
            }
        });

        binding.registerButtonNext.setOnClickListener(v -> {
            if (validateInputs()) {
                User user = new User(
                        binding.registerUsernameTIL.getEditText().getText().toString().trim(),
                        binding.registerPasswordTIL.getEditText().getText().toString().trim(),
                        binding.registerEmailTIL.getEditText().getText().toString().trim()
                );

                Userinfo userinfo = new Userinfo(
                        null,
                        binding.registerFirstNameTIL.getEditText().getText().toString().trim(),
                        binding.registerLastNameTIL.getEditText().getText().toString().trim(),
                        binding.registerUsernameTIL.getEditText().getText().toString().trim(),
                        binding.registerCountryTIL.getEditText().getText().toString().trim(),
                        binding.registerAddressTIL.getEditText().getText().toString().trim(),
                        binding.registerBioTIL.getEditText().getText().toString().trim(),
                        getCurrentFormattedDate(),
                        "profile_picture_url_placeholder"
                );

                mViewModel.registerUser(user, userinfo);
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                navigateBackToLogin();
            }
        });

    }

    private void navigateBackToLogin() {
        NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_loginFragment);
    }

    public static String getCurrentFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(binding.registerEmailTIL.getEditText().getText())) {
            binding.registerEmailTIL.setError("Email is required");
            isValid = false;
        } else {
            binding.registerEmailTIL.setError(null);
        }

        if (TextUtils.isEmpty(binding.registerPasswordTIL.getEditText().getText())) {
            binding.registerPasswordTIL.setError("Password is required");
            isValid = false;
        } else {
            binding.registerPasswordTIL.setError(null);
        }

        return isValid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
