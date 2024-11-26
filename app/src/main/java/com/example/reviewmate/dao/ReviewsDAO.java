package com.example.reviewmate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import com.example.reviewmate.model.Review;

import java.util.List;

@Dao
public interface ReviewsDAO {

    // Insert a new review into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Review review);

    @Query("SELECT * FROM reviews WHERE movie_id = :movieId ORDER BY review_date DESC")
    LiveData<List<Review>> getReviewsByMovieId(int movieId);

    @Query("SELECT * FROM reviews WHERE user_id = :userId ORDER BY review_date DESC")
    LiveData<List<Review>> getReviewsByUserId(int userId);

    @Query("DELETE FROM reviews WHERE review_id = :reviewId")
    void deleteReviewById(int reviewId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT reviews.*, userinfo.username FROM reviews INNER JOIN userinfo ON reviews.user_id = userinfo.user_id WHERE reviews.movie_id = :movieId ORDER BY review_date DESC")
    LiveData<List<Review>> getReviewsWithUsernamesByMovieId(int movieId);

    @Query("SELECT userinfo.username FROM reviews INNER JOIN userinfo ON reviews.user_id = userinfo.user_id WHERE reviews.movie_id = :movieId ORDER BY review_date DESC")
    LiveData<List<String>> getUsernamesByMovieId(int movieId);
}
