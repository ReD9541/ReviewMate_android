package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "movies_watched",
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
public class MoviesWatched {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "user_id")
    private Integer userId;

    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "watch_date")
    private String watchDate;

    // Default no-argument constructor required by Room
    public MoviesWatched() {
    }

    @Ignore
    public MoviesWatched(Integer userId, Integer movieId, String watchDate) {
        this.userId = userId;
        this.movieId = movieId;
        this.watchDate = watchDate;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
