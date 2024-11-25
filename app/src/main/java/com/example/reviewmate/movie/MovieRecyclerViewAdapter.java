package com.example.reviewmate.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.R;
import com.example.reviewmate.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private final List<Movie> movieList = new ArrayList<>();
    private final OnMovieClickListener onMovieClickListener;
    private final int layoutResourceId;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieRecyclerViewAdapter(OnMovieClickListener listener, int layoutResourceId) {
        this.onMovieClickListener = listener;
        this.layoutResourceId = layoutResourceId;
    }

    public void submitList(List<Movie> movies) {
        if (movies != null) {
            movieList.clear();
            movieList.addAll(movies);
            notifyDataSetChanged();
        }
    }

    public void addMovie(Movie movie) {
        movieList.add(movie);
        notifyItemInserted(movieList.size() - 1);
    }

    public void removeMovie(int position) {
        if (position >= 0 && position < movieList.size()) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updateMovie(int position, Movie movie) {
        if (position >= 0 && position < movieList.size()) {
            movieList.set(position, movie);
            notifyItemChanged(position);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (position < movieList.size()) {
            Movie movie = movieList.get(position);
            holder.bind(movie);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView posterImageView;
        private final TextView titleTextView;
        private final TextView releaseDateTextView;
        private final TextView runtimeTextView;
        private final TextView imdbRatingTextView;
        private final TextView userRatingTextView;
        private final TextView descriptionTextView;

        MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
            runtimeTextView = itemView.findViewById(R.id.runtimeTextView);
            imdbRatingTextView = itemView.findViewById(R.id.imdbRatingTextView);
            userRatingTextView = itemView.findViewById(R.id.userRatingTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

            itemView.setOnClickListener(v -> {
                if (onMovieClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onMovieClickListener.onMovieClick(movieList.get(getAdapterPosition()));
                }
            });
        }

        void bind(Movie movie) {
            titleTextView.setText(movie.getTitle());
            releaseDateTextView.setText("Release Date: " + movie.getReleaseDate());
            runtimeTextView.setText("Runtime: " + movie.getRuntime() + " min");
            imdbRatingTextView.setText("IMDb Rating: " + movie.getImdbRating());
            userRatingTextView.setText("User Rating: " + movie.getUserRating());
            descriptionTextView.setText(movie.getDescription());

            // Load poster image using Picasso
            Picasso.get()
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.ic_default_poster)
                    .into(posterImageView);
        }
    }
}
