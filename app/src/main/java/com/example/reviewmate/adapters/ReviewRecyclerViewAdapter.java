package com.example.reviewmate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.R;
import com.example.reviewmate.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    private final List<Review> reviewList = new ArrayList<>();
    private final List<String> usernameList = new ArrayList<>();

    // Creates a new ViewHolder instance for a review item layout.

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    // Binds the review and username data to the ViewHolder at a specific position.

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if (position < reviewList.size()) {
            Review review = reviewList.get(position);
            String username = usernameList.get(position);
            holder.bind(review, username);
        }
    }

    // Returns the total number of reviews in the list.

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void submitList(List<Review> reviews, List<String> usernames) {
        reviewList.clear();
        usernameList.clear();
        if (reviews != null && usernames != null && reviews.size() == usernames.size()) {
            reviewList.addAll(reviews);
            usernameList.addAll(usernames);
        }
        notifyDataSetChanged();
    }
    // ViewHolder manages individual review item views and binds data to them.

    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final TextView usernameTextView;
        private final RatingBar ratingBar;
        private final TextView reviewDateTextView;
        private final TextView reviewTextView;

        ReviewViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            ratingBar = itemView.findViewById(R.id.reviewRatingBar);
            reviewDateTextView = itemView.findViewById(R.id.reviewDateTextView);
            reviewTextView = itemView.findViewById(R.id.reviewTextView);
        }
        // Binds review data and username to the ViewHolder UI elements.

        void bind(Review review, String username) {
            usernameTextView.setText(username);
            ratingBar.setRating(review.getRating());
            reviewDateTextView.setText(review.getReviewDate());
            reviewTextView.setText(review.getReviewText());
        }
    }
}
