package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "movie_directors",
        primaryKeys = {"movie_id", "director_id"},
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "movie_id",
                        childColumns = "movie_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Director.class,
                        parentColumns = "director_id",
                        childColumns = "director_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class MovieDirector {

    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "director_id")
    private Integer directorId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public MovieDirector(Integer movieId, Integer directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }


}
