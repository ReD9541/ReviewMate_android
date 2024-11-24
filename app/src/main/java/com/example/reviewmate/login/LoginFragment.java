package com.example.reviewmate.login;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.reviewmate.R;
import com.example.reviewmate.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private LoginFragmentBinding binding;

    private final String successfulLoginMessage = "Welcome, you're logged in";
    private final String invalidEmailOrPasswordMessage = "Invalid email or password";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Use ViewBinding to inflate the view
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.loginLoginButton.setOnClickListener(v -> {
            String email = null;
            String password = null;

            if (binding.loginEmailTIL.getEditText() != null) {
                email = binding.loginEmailTIL.getEditText().getText().toString().trim();
            }
            if (binding.loginPasswordTIL.getEditText() != null) {
                password = binding.loginPasswordTIL.getEditText().getText().toString().trim();
            }

            if (validateInput(email, password)) {
                mViewModel.login(email, password);
            }
        });

        mViewModel.getLoginStatus().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                showToast(successfulLoginMessage);
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
            } else {
                binding.loginEmailTIL.setError(invalidEmailOrPasswordMessage);
                showToast(invalidEmailOrPasswordMessage);
            }
        });

        binding.loginSignUpButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateInput(String email, String password) {
        boolean isValid = true;

        // Validate email
        if (email == null || email.isEmpty()) {
            // Messages for toast
            String emailRequiredMessage = "Email is required";
            binding.loginEmailTIL.setError(emailRequiredMessage);
            showToast(emailRequiredMessage);
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            String invalidEmailMessage = "Please enter a valid email address";
            binding.loginEmailTIL.setError(invalidEmailMessage);
            showToast(invalidEmailMessage);
            isValid = false;
        } else {
            binding.loginEmailTIL.setError(null);
        }

        // Validate password
        if (password == null || password.isEmpty()) {
            String passwordRequiredMessage = "Password is required";
            binding.loginPasswordTIL.setError(passwordRequiredMessage);
            showToast(passwordRequiredMessage);
            isValid = false;
        } else if (password.length() < 4) {
            String shortPasswordMessage = "Password must be at least 4 characters";
            binding.loginPasswordTIL.setError(shortPasswordMessage);
            showToast(shortPasswordMessage);
            isValid = false;
        } else {
            binding.loginPasswordTIL.setError(null);
        }

        return isValid;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
