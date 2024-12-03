package com.example.reviewmate.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.R;
import com.example.reviewmate.adapters.ReviewRecyclerViewAdapter;
import com.example.reviewmate.login.LoginFragment;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Review;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailFragment extends Fragment {

    private MovieDetailViewModel movieDetailViewModel;
    private ReviewViewModel reviewViewModel;
    private ImageView posterImageView;
    private TextView titleTextView, releaseDateTextView, runtimeTextView, imdbRatingTextView, userRatingTextView, directorTextView, castTextView, languageTextView, countryTextView, ageRatingTextView, budgetTextView, boxOfficeTextView, descriptionTextView;
    private WebView trailerWebView;
    private RecyclerView reviewsRecyclerView;
    private ReviewRecyclerViewAdapter reviewAdapter;
    private CheckBox watchlistCheckBox, watchedCheckBox;
    private RatingBar ratingBar;
    private EditText reviewEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);

        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        reviewAdapter = new ReviewRecyclerViewAdapter();
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecyclerView.setAdapter(reviewAdapter);

        if (getArguments() != null) {
            int movieId = getArguments().getInt("MOVIE_ID", -1);
            if (movieId != -1) {
                observeMovieDetails(movieId);
                loadMovieReviews(movieId);
                setupCheckboxInteractions(movieId);
                setupReviewSubmission(movieId);
            }
        }
    }

    private void initializeViews(View view) {
        posterImageView = view.findViewById(R.id.posterImageView);
        titleTextView = view.findViewById(R.id.titleTextView);
        releaseDateTextView = view.findViewById(R.id.releaseDateTextView);
        runtimeTextView = view.findViewById(R.id.runtimeTextView);
        imdbRatingTextView = view.findViewById(R.id.imdbRatingTextView);
        userRatingTextView = view.findViewById(R.id.userRatingTextView);
        directorTextView = view.findViewById(R.id.directorTextView);
        castTextView = view.findViewById(R.id.castTextView);
        languageTextView = view.findViewById(R.id.languageTextView);
        countryTextView = view.findViewById(R.id.countryTextView);
        ageRatingTextView = view.findViewById(R.id.ageRatingTextView);
        budgetTextView = view.findViewById(R.id.budgetTextView);
        boxOfficeTextView = view.findViewById(R.id.boxOfficeTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        trailerWebView = view.findViewById(R.id.trailerWebView);
        reviewsRecyclerView = view.findViewById(R.id.reviewsRecyclerView);
        watchlistCheckBox = view.findViewById(R.id.watchlistCheckBox);
        watchedCheckBox = view.findViewById(R.id.watchedCheckBox);
        ratingBar = view.findViewById(R.id.ratingBar);
        reviewEditText = view.findViewById(R.id.reviewEditText);
    }

    private void observeMovieDetails(int movieId) {
        movieDetailViewModel.getMovieDetails(movieId).observe(getViewLifecycleOwner(), this::populateMovieDetails);
    }

    private void loadMovieReviews(int movieId) {
        reviewViewModel.getReviewsWithUsernamesByMovieId(movieId).observe(getViewLifecycleOwner(), reviews -> {
            if (reviews != null) {
                // Fetch usernames associated with the reviews
                reviewViewModel.getUsernamesByMovieId(movieId).observe(getViewLifecycleOwner(), usernames -> {
                    if (usernames != null && usernames.size() == reviews.size()) {
                        reviewAdapter.submitList(reviews, usernames);
                    } else {
                        reviewAdapter.submitList(List.of(), List.of());
                    }
                });
            } else {
                reviewAdapter.submitList(List.of(), List.of());
            }
        });
    }


    private void populateMovieDetails(Movie movie) {
        if (movie != null) {
            titleTextView.setText(movie.getTitle());
            releaseDateTextView.setText("Release Date: " + movie.getReleaseDate());
            runtimeTextView.setText("Runtime: " + movie.getRuntime() + " min");
            imdbRatingTextView.setText("IMDb Rating: " + movie.getImdbRating());
            userRatingTextView.setText("User Rating: " + movie.getUserRating());
            directorTextView.setText("Director: " + movie.getDirector());
            castTextView.setText("Casts: " + movie.getCast());
            languageTextView.setText("Language: " + movie.getLanguage());
            countryTextView.setText("Country: " + movie.getCountry());
            ageRatingTextView.setText("Age Rating: " + movie.getAgeRating());
            budgetTextView.setText("Budget: $" + movie.getBudget());
            boxOfficeTextView.setText("Box Office: $" + movie.getBoxOffice());
            descriptionTextView.setText(movie.getDescription());

            Picasso.get()
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.ic_default_poster)
                    .error(R.drawable.ic_default_poster)
                    .into(posterImageView);

            loadTrailer(movie.getTrailerUrl());
        }
    }

    private void loadTrailer(String trailerUrl) {
        if (trailerUrl != null && !trailerUrl.isEmpty()) {
            String embedUrl = getEmbedUrl(trailerUrl);

            trailerWebView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = trailerWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            trailerWebView.clearCache(true);
            trailerWebView.loadUrl(embedUrl);
        }
    }

    private String getEmbedUrl(String youtubeUrl) {
        if (youtubeUrl.contains("watch?v=")) {
            return youtubeUrl.replace("watch?v=", "embed/");
        }
        return youtubeUrl;
    }

    private void setupCheckboxInteractions(int movieId) {
        int userId = LoginFragment.loggedInUserID;

        movieDetailViewModel.isMovieInWatchlist(userId, movieId).observe(getViewLifecycleOwner(), isInWatchlist -> {
            watchlistCheckBox.setChecked(isInWatchlist != null && isInWatchlist);
        });

        movieDetailViewModel.isMovieInWatchedList(userId, movieId).observe(getViewLifecycleOwner(), isInWatchedList -> {
            watchedCheckBox.setChecked(isInWatchedList != null && isInWatchedList);
        });

        watchlistCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                movieDetailViewModel.addToWatchlist(userId, movieId);
                Toast.makeText(requireContext(), "Added to Watchlist", Toast.LENGTH_SHORT).show();
            } else {
                movieDetailViewModel.removeFromWatchlist(userId, movieId);
                Toast.makeText(requireContext(), "Removed from Watchlist", Toast.LENGTH_SHORT).show();
            }
        });

        watchedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                movieDetailViewModel.addToWatchedList(userId, movieId);
                Toast.makeText(requireContext(), "Marked as Watched", Toast.LENGTH_SHORT).show();
            } else {
                movieDetailViewModel.removeFromWatchedList(userId, movieId);
                Toast.makeText(requireContext(), "Removed from Watched List", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupReviewSubmission(int movieId) {
        int userId = LoginFragment.loggedInUserID;

        getView().findViewById(R.id.submitReviewButton).setOnClickListener(v -> {
            String reviewText = reviewEditText.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (!reviewText.isEmpty() && rating > 0) {
                reviewViewModel.hasReviewed(userId, movieId).observe(getViewLifecycleOwner(), hasReviewed -> {
                    if (hasReviewed != null && hasReviewed) {
                        Toast.makeText(requireContext(), "You have already reviewed this movie.", Toast.LENGTH_SHORT).show();
                    } else {
                        Review review = new Review();
                        review.setMovieId(movieId);
                        review.setUserId(userId);
                        review.setReviewText(reviewText);
                        review.setRating((int) rating);
                        review.setReviewDate(getCurrentDate());

                        reviewViewModel.submitReview(review);
                        Toast.makeText(requireContext(), "Review submitted!", Toast.LENGTH_SHORT).show();

                        reviewEditText.setText("");
                        ratingBar.setRating(5);
                        loadMovieReviews(movieId);
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Please provide a rating and review text.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
