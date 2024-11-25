package com.example.reviewmate.movie;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.MovieSnippetsBinding;
import com.example.reviewmate.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private final MovieSnippetsBinding binding;

    public MovieViewHolder(@NonNull MovieSnippetsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Movie movie, MovieRecyclerViewAdapter.OnMovieClickListener onItemClickListener) {
        binding.titleTextView.setText(movie.getTitle());

        binding.imdbRatingTextView.setText("IMDb Rating: " + movie.getImdbRating());

        if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty()) {
            binding.releaseDateTextView.setVisibility(View.VISIBLE);
            binding.releaseDateTextView.setText("Release Date: " + movie.getReleaseDate());
        } else {
            binding.releaseDateTextView.setVisibility(View.GONE);
        }

        if (movie.getRuntime() != null && movie.getRuntime() > 0) {
            binding.runtimeTextView.setVisibility(View.VISIBLE);
            binding.runtimeTextView.setText("Runtime: " + movie.getRuntime() + " min");
        } else {
            binding.runtimeTextView.setVisibility(View.GONE);
        }

        if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
            Picasso.get()
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.ic_default_poster) // Placeholder image
                    .error(R.drawable.ic_default_poster)       // Error image
                    .into(binding.posterImageView);
        } else {
            binding.posterImageView.setImageResource(R.drawable.ic_default_poster);
        }

        itemView.setOnClickListener(v -> onItemClickListener.onMovieClick(movie));
    }
}
