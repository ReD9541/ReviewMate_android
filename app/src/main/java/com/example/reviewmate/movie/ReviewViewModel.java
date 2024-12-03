package com.example.reviewmate.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.ReviewRepository;
import com.example.reviewmate.model.Review;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {
    private final ReviewRepository reviewRepository;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
    }

    public LiveData<List<Review>> getReviewsWithUsernamesByMovieId(int movieId) {
        return reviewRepository.getReviewsWithUsernamesByMovieId(movieId);
    }

    public LiveData<List<String>> getUsernamesByMovieId(int movieId) {
        return reviewRepository.getUsernamesByMovieId(movieId);
    }

    public void submitReview(Review review) {
        reviewRepository.addReview(review);
    }

    public LiveData<Boolean> hasReviewed(int userId, int movieId) {
        return reviewRepository.hasReviewed(userId, movieId);
    }


}
