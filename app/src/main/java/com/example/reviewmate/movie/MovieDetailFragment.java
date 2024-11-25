package com.example.reviewmate.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reviewmate.R;
import com.example.reviewmate.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {

    private ImageView posterImageView;
    private TextView titleTextView, releaseDateTextView, runtimeTextView, imdbRatingTextView, userRatingTextView, descriptionTextView;
    private WebView trailerWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        posterImageView = view.findViewById(R.id.posterImageView);
        titleTextView = view.findViewById(R.id.titleTextView);
        releaseDateTextView = view.findViewById(R.id.releaseDateTextView);
        runtimeTextView = view.findViewById(R.id.runtimeTextView);
        imdbRatingTextView = view.findViewById(R.id.imdbRatingTextView);
        userRatingTextView = view.findViewById(R.id.userRatingTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        trailerWebView = view.findViewById(R.id.trailerWebView);

        // Retrieve movie details from the arguments
        if (getArguments() != null) {
            Movie movie = (Movie) getArguments().getSerializable("MOVIE_DETAILS");
            if (movie != null) {
                populateMovieDetails(movie);
            }
        }
    }

    private void populateMovieDetails(Movie movie) {
        titleTextView.setText(movie.getTitle());
        releaseDateTextView.setText("Release Date: " + movie.getReleaseDate());
        runtimeTextView.setText("Runtime: " + movie.getRuntime() + " min");
        imdbRatingTextView.setText("IMDb Rating: " + movie.getImdbRating());
        userRatingTextView.setText("User Rating: " + movie.getUserRating());
        descriptionTextView.setText(movie.getDescription());

        // Load poster image with Picasso
        Picasso.get()
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_default_poster)
                .error(R.drawable.ic_default_poster)
                .into(posterImageView);

        String trailerUrl = movie.getTrailerUrl();
        String embedUrl = getEmbedUrl(trailerUrl);
        trailerWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = trailerWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        trailerWebView.loadUrl(embedUrl);
    }

    private String getEmbedUrl(String youtubeUrl) {
        return youtubeUrl.replace("watch?v=", "embed/");
    }
}
