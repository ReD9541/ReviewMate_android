package com.example.reviewmate.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.R;
import com.example.reviewmate.adapters.MovieRecyclerViewAdapter;
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

        // Bind poster using Picasso
        if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
            Picasso.get()
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.ic_default_poster)
                    .error(R.drawable.ic_default_poster)
                    .into(binding.posterImageView);
        } else {
            binding.posterImageView.setImageResource(R.drawable.ic_default_poster);
        }

        itemView.setOnClickListener(v -> onItemClickListener.onMovieClick(movie));
    }
}
