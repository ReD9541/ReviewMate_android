package com.example.reviewmate.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.reviewmate.databinding.MovieDetailsBinding;
import com.example.reviewmate.model.Movie;

public class MovieRecyclerViewAdapter extends ListAdapter<Movie, MovieViewHolder> {

    private OnItemClickListener onItemClickListener;

    public MovieRecyclerViewAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getMovieId().equals(newItem.getMovieId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getImdbRating().equals(newItem.getImdbRating()) &&
                    oldItem.getPosterUrl().equals(newItem.getPosterUrl());
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieDetailsBinding binding = MovieDetailsBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.updateMovie(movie);
        holder.bind(movie, onItemClickListener);
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
