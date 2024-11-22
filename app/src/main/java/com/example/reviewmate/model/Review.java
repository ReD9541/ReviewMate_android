package com.example.reviewmate.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "reviews",
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "movie_id",
                        childColumns = "movie_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = CASCADE
                ),
        },
        indices = {
                @Index(value = "movie_id"),
                @Index(value = "user_id"),
                @Index(value = {"movie_id", "user_id"}, unique = true)
        }
)
public class Review {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    private Integer id;

    @ColumnInfo(name = "rating")
    private Float rating;

    @ColumnInfo(name = "review_text")
    private String reviewText;

    @ColumnInfo(name = "review_date")
    private Date reviewDate;

    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "user_id")
    private Integer userId;

    @Ignore
    public Review() {
    }

    public Review(Float rating, String reviewText, Date reviewDate, Integer movieId, Integer userId) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.movieId = movieId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                ", reviewDate=" + reviewDate +
                ", movieId=" + movieId +
                ", userId=" + userId +
                '}';
    }
}
