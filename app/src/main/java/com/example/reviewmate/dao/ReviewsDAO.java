package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reviewmate.model.Review;

import java.util.List;

@Dao
public interface ReviewsDAO {

    @Insert
    void insert(Review review);

    @Update
    void update(Review review);

    @Delete
    void delete(Review review);

    @Query("SELECT * FROM reviews WHERE movie_id = :movieId")
    List<Review> getReviewsByMovie(int movieId);

    @Query("SELECT * FROM reviews WHERE user_id = :userId")
    List<Review> getReviewsByUser(int userId);

    @Query("SELECT * FROM reviews")
    List<Review> getAllReviews();
}
