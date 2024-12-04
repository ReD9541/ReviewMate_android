package com.example.reviewmate;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupWindowInsets();
        setupBottomNavigation();
    }
// Handles window insets to manage system bars and IME (keyboard) padding.
// doesn't work, need to implement something better so the keyboard doesn't cover the UI elements and push the bottom navbar up
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            int systemBarsInsetBottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            int imeInsetBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;

            int bottomPadding = Math.max(systemBarsInsetBottom, imeInsetBottom);

            if (bottomNavigationView != null) {
                bottomNavigationView.setPadding(0, 0, 0, bottomPadding);
            }

            return insets;
        });
    }

// Configures the BottomNavigationView with navigation components.

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        FragmentContainerView navHostFragmentContainer = findViewById(R.id.nav_host_fragment);
        if (navHostFragmentContainer != null) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);

            if (navHostFragment != null) {
                NavController navController = navHostFragment.getNavController();
                NavigationUI.setupWithNavController(bottomNavigationView, navController);

                navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                    int[] fragmentsToHideBottomNav = new int[]{
                            R.id.loginFragment,
                            R.id.registerFragment
                    };

                    boolean shouldHideBottomNav = false;
                    for (int id : fragmentsToHideBottomNav) {
                        if (destination.getId() == id) {
                            shouldHideBottomNav = true;
                            break;
                        }
                    }

                    if (shouldHideBottomNav) {
                        bottomNavigationView.setVisibility(View.GONE);
                    } else {
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }
}
