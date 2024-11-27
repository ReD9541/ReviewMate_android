package com.example.reviewmate.movie;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.databinding.MovieSnippetsBinding;
import com.example.reviewmate.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final List<Movie> movieList = new ArrayList<>();
    private final OnMovieClickListener onMovieClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieRecyclerViewAdapter(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    public void submitList(List<Movie> movies) {
        if (movies != null) {
            movieList.clear();
            movieList.addAll(movies);
            notifyDataSetChanged();
        }
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
        if (position < movieList.size()) {
            Movie movie = movieList.get(position);
            holder.bind(movie, onMovieClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
