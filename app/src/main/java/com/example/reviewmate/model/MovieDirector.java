package com.example.reviewmate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie_directors",
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "movie_id",
                        childColumns = "movie_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Director.class,
                        parentColumns = "director_id",
                        childColumns = "director_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index(value = "movie_id"), @Index(value = "director_id")}
)
public class MovieDirector implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "movie_id")
    @NonNull
    private Integer movieId;

    @ColumnInfo(name = "director_id")
    @NonNull
    private Integer directorId;

    @Ignore
    public MovieDirector() {
    }

    @Ignore
    public MovieDirector(@NonNull Integer movieId, @NonNull Integer directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }

    // Constructor with all fields (including `id`)
    public MovieDirector(Integer id, @NonNull Integer movieId, @NonNull Integer directorId) {
        this.id = id;
        this.movieId = movieId;
        this.directorId = directorId;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull Integer movieId) {
        this.movieId = movieId;
    }

    @NonNull
    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(@NonNull Integer directorId) {
        this.directorId = directorId;
    }
}
