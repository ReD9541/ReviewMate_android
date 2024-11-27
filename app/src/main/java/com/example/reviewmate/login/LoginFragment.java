package com.example.reviewmate.login;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.reviewmate.R;
import com.example.reviewmate.data.ReviewMateRoomDatabase;
import com.example.reviewmate.databinding.LoginFragmentBinding;
import com.example.reviewmate.model.User;
import com.example.reviewmate.profile.SharedViewModel;

public class LoginFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private LoginViewModel mViewModel;
    private LoginFragmentBinding binding;

    // Global variables for logged-in user info
    public static String loggedInUserEmail;
    public static String loggedInUserName;
    public static Integer loggedInUserID;
    private final String successfulLoginMessage = "Welcome, you're logged in";
    private final String invalidEmailOrPasswordMessage = "Invalid email or password";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReviewMateRoomDatabase.getDatabase(requireContext());
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
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
                login(email, password, v);
            }
        });

        binding.loginSignUpButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private void login(String email, String password, View view) {
        mViewModel.login(email, password);
        mViewModel.getLoginStatus().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                mViewModel.getLoggedInUserId().observe(getViewLifecycleOwner(), userId -> {
                    if (userId != null && userId != -1) {
                        sharedViewModel.setUserId(userId);

                        mViewModel.findById(email).observe(getViewLifecycleOwner(), user -> {
                            if (user != null) {
                                loggedInUserEmail = user.getEmail();
                                loggedInUserName = user.getUsername();
                                loggedInUserID = user.getId();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("USER_DETAILS", user);

                                showToast(successfulLoginMessage);

                                try {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                                    navController.navigate(R.id.action_loginFragment_to_homeFragment, bundle);
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                    showToast("Navigation error, please try again later.");
                                }
                            } else {
                                showToast("User not found.");
                            }
                        });

                    } else {
                        showToast("User ID not found.");
                    }
                });
            } else {
                binding.loginEmailTIL.setError(invalidEmailOrPasswordMessage);
                showToast(invalidEmailOrPasswordMessage);
            }
        });
    }


    // Validate Input Method
    private boolean validateInput(String email, String password) {
        boolean isValid = true;

        // Validate email
        if (email == null || email.isEmpty()) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
