package com.example.reviewmate.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.databinding.ProfileReviewBinding;
import com.example.reviewmate.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ProfileReviewsAdapter extends RecyclerView.Adapter<ProfileReviewsAdapter.ReviewViewHolder> {

    private final List<Review> reviews = new ArrayList<>();
    private final List<String> movieNames = new ArrayList<>();

    // Updates the adapter with a new list of reviews and their corresponding movie names.

    public void submitList(List<Review> reviewList, List<String> movieNameList) {
        reviews.clear();
        movieNames.clear();

        if (reviewList != null && movieNameList != null && reviewList.size() == movieNameList.size()) {
            reviews.addAll(reviewList);
            movieNames.addAll(movieNameList);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProfileReviewBinding binding = ProfileReviewBinding.inflate(inflater, parent, false);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if (position < reviews.size() && position < movieNames.size()) {
            Review review = reviews.get(position);
            String movieName = movieNames.get(position);
            holder.bind(review, movieName);
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
// Creates a new ReviewViewHolder for the profile reviews list.

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final ProfileReviewBinding binding;

        public ReviewViewHolder(@NonNull ProfileReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
// Binds the review and movie name data to the ViewHolder for a specific position.

        public void bind(Review review, String movieName) {
            binding.movienameTextView.setText(movieName);
            binding.reviewRatingBar.setRating(review.getRating());
            binding.reviewDateTextView.setText(review.getReviewDate());
            binding.reviewTextView.setText(review.getReviewText());
        }
    }
}
