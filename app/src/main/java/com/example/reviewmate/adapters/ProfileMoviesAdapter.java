package com.example.reviewmate.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.MovieSnippetsBinding;
import com.example.reviewmate.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileMoviesAdapter extends RecyclerView.Adapter<ProfileMoviesAdapter.MovieViewHolder> {

    private final List<Movie> movies = new ArrayList<>();
    private final OnMovieClickListener onMovieClickListener;

    // Interface to handle movie item click events in the profile.
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    // Constructor initializes the adapter with a click listener for movies.
    public ProfileMoviesAdapter(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    // Updates the movie list in the adapter and refreshes the UI.
    public void submitList(List<Movie> movieList) {
        movies.clear();
        if (movieList != null) {
            movies.addAll(movieList);
        }
        notifyDataSetChanged();
    }

    // Creates a new MovieViewHolder instance for a movie snippet.
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieSnippetsBinding binding = MovieSnippetsBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding, onMovieClickListener);
    }

    // Binds the movie data to the holder at a specific position.
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    // Returns the number of movies in the adapter.
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // ViewHolder bind movie data and handle click events.
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final MovieSnippetsBinding binding;
        private final OnMovieClickListener onMovieClickListener;

        public MovieViewHolder(@NonNull MovieSnippetsBinding binding, OnMovieClickListener onMovieClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onMovieClickListener = onMovieClickListener;
        }

        public void bind(Movie movie) {
            // Set movie title
            binding.titleTextView.setText(movie.getTitle());

            // Load poster image using Picasso
            if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
                Picasso.get()
                        .load(movie.getPosterUrl())
                        .placeholder(R.drawable.ic_default_poster)
                        .error(R.drawable.ic_default_poster)
                        .into(binding.posterImageView);
            } else {
                binding.posterImageView.setImageResource(R.drawable.ic_default_poster);
            }

            itemView.setOnClickListener(v -> {
                if (onMovieClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onMovieClickListener.onMovieClick(movie);
                }
            });
        }
    }
}
