package com.example.reviewmate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reviewmate.model.Movie;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void insert(Movie movie);

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM movie")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movie ORDER BY imdb_rating DESC LIMIT 4")
    LiveData<List<Movie>> getTopRatedMovies();

    @Query("SELECT * FROM movie ORDER BY release_date DESC LIMIT 4")
    LiveData<List<Movie>> getLatestMovies();
}
