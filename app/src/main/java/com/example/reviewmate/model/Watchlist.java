package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

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
public class Watchlist {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "user_id")
    private Integer userId;

    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "added_date")
    private String addedDate;

    // Default no-argument constructor required by Room
    public Watchlist() {
    }

    // Constructor for creating Watchlist instances
    @Ignore
    public Watchlist(Integer userId, Integer movieId, String addedDate) {
        this.userId = userId;
        this.movieId = movieId;
        this.addedDate = addedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
}
