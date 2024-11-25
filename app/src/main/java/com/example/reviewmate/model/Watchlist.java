package com.example.reviewmate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

import java.io.Serializable;

@Entity(tableName = "watchlist",
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
public class Watchlist implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "user_id")
    private Integer userId;

    @NonNull
    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "added_date")
    private String addedDate;

    @Ignore
    public Watchlist() {
    }

    public Watchlist(@NonNull Integer userId, @NonNull Integer movieId, String addedDate) {
        this.userId = userId;
        this.movieId = movieId;
        this.addedDate = addedDate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
}
