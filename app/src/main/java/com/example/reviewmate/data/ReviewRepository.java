package com.example.reviewmate.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.reviewmate.dao.ReviewsDAO;
import com.example.reviewmate.model.Review;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ReviewRepository {

    private final ReviewsDAO reviewsDAO;
    private final ExecutorService executorService;

    public ReviewRepository(Application application) {
        ReviewMateRoomDatabase db = ReviewMateRoomDatabase.getDatabase(application);
        reviewsDAO = db.reviewsDAO();
        executorService = ReviewMateRoomDatabase.databaseWriteExecutor;
    }

    public LiveData<List<Review>> getReviewsByMovieId(int movieId) {
        return reviewsDAO.getReviewsByMovieId(movieId);
    }

    public LiveData<List<Review>> getReviewsWithUsernamesByMovieId(int movieId) {
        return reviewsDAO.getReviewsWithUsernamesByMovieId(movieId);
    }

    public LiveData<List<String>> getUsernamesByMovieId(int movieId) {
        return reviewsDAO.getUsernamesByMovieId(movieId);
    }

    public void addReview(Review review) {
        executorService.execute(() -> reviewsDAO.insertReview(review));
    }

    public void deleteReview(int reviewId) {
        executorService.execute(() -> reviewsDAO.deleteReviewById(reviewId));
    }

    public LiveData<Boolean> hasReviewed(int userId, int movieId) {
        return reviewsDAO.hasReviewed(userId, movieId);
    }

//same as other repository to read/write data from and to the database
}