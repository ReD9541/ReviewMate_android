package com.example.reviewmate.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.databinding.ItemReviewBinding;
import com.example.reviewmate.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ProfileReviewsAdapter extends RecyclerView.Adapter<ProfileReviewsAdapter.ReviewViewHolder> {

    private final List<Review> reviews = new ArrayList<>();

    public void submitList(List<Review> reviewList) {
        reviews.clear();
        reviews.addAll(reviewList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemReviewBinding binding = ItemReviewBinding.inflate(inflater, parent, false);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final ItemReviewBinding binding;

        public ReviewViewHolder(@NonNull ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Review review) {
            // Set the movie name instead of username
            binding.usernameTextView.setText(review.getMovieName());

            // Set other review details
            binding.reviewRatingBar.setRating(review.getRating());
            binding.reviewDateTextView.setText(review.getReviewDate());
            binding.reviewTextView.setText(review.getReviewText());
        }
    }
}
