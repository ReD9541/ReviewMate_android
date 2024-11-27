package com.example.reviewmate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import static androidx.room.ForeignKey.CASCADE;

import java.io.Serializable;

@Entity(tableName = "reviews",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = Movie.class,
                        parentColumns = "movie_id",
                        childColumns = "movie_id",
                        onDelete = CASCADE)
        },
        indices = {@Index(value = "user_id"), @Index(value = "movie_id")}
)
public class Review implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    private Integer reviewId;

    @NonNull
    @ColumnInfo(name = "user_id")
    private Integer userId;

    @NonNull
    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @NonNull
    @ColumnInfo(name = "rating")
    private Integer rating;

    @ColumnInfo(name = "review_text")
    private String reviewText;

    @ColumnInfo(name = "review_date")
    private String reviewDate;

    @Ignore
    private transient String username;

    @Ignore
    private transient String moviename;

    @Ignore
    public Review() {
    }

    @Ignore
    public Review(@NonNull Integer userId, @NonNull Integer movieId, @NonNull Integer rating, String reviewText, String reviewDate) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    // Full constructor with all fields
    public Review(Integer reviewId, @NonNull Integer userId, @NonNull Integer movieId, @NonNull Integer rating, String reviewText, String reviewDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    // Getters and setters
    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    @NonNull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    @NonNull
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull Integer movieId) {
        this.movieId = movieId;
    }

    @NonNull
    public Integer getRating() {
        return rating;
    }

    public void setRating(@NonNull Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    public String getUsername() {
        return username;
    }
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    public void setUsername(String username) {
        this.username = username;
    }
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    public String getMovieName() {
        return moviename;
    }

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    public void setMovieName(String movieName) {
        this.moviename = movieName;
    }
}
