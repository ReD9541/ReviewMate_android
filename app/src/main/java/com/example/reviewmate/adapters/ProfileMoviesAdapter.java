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

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public ProfileMoviesAdapter(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void submitList(List<Movie> movieList) {
        movies.clear();
        if (movieList != null) {
            movies.addAll(movieList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieSnippetsBinding binding = MovieSnippetsBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding, onMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

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
