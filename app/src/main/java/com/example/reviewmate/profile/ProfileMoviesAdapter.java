package com.example.reviewmate.profile;

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

    public void submitList(List<Movie> movieList) {
        movies.clear();
        movies.addAll(movieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieSnippetsBinding binding = MovieSnippetsBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding);
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

        public MovieViewHolder(@NonNull MovieSnippetsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            // Set the movie title
            binding.titleTextView.setText(movie.getTitle());

            if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
                Picasso.get()
                        .load(movie.getPosterUrl())
                        .placeholder(R.drawable.ic_default_poster)
                        .error(R.drawable.ic_default_poster)
                        .into(binding.posterImageView);
            } else {

                binding.posterImageView.setImageResource(R.drawable.ic_default_poster);
            }
        }
    }
}
