package com.example.reviewmate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
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
    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<Movie> getMovieDetails(int movieId);
    @Query("SELECT * FROM movie INNER JOIN movies_watched ON movie.movie_id = movies_watched.movie_id WHERE movies_watched.user_id = :userId")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<Movie>> getMoviesWatchedByUserId(int userId);

    @Query("SELECT * FROM movie INNER JOIN watchlist ON movie.movie_id = watchlist .movie_id WHERE watchlist.user_id = :userId")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<Movie>> getMoviesWatchlistedByUserId(int userId);
}
