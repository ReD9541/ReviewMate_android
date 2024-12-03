package com.example.reviewmate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.MoviesWatched;
import com.example.reviewmate.model.Watchlist;

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

    @Query("SELECT * FROM movie INNER JOIN watchlist ON movie.movie_id = watchlist.movie_id WHERE watchlist.user_id = :userId")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<Movie>> getMoviesWatchlistedByUserId(int userId);

    @Insert
    void addToWatchlist(Watchlist watchlist); // Accepts Watchlist entity

    @Query("DELETE FROM watchlist WHERE movie_id = :movieId AND user_id = :userId")
    void removeFromWatchlist(int movieId, int userId);

    @Insert
    void addToWatchedList(MoviesWatched moviesWatched); // Accepts MoviesWatched entity

    @Query("DELETE FROM movies_watched WHERE movie_id = :movieId AND user_id = :userId")
    void removeFromWatchedList(int movieId, int userId);

    @Query("SELECT * FROM movie WHERE title LIKE :query AND genre LIKE :genre AND language LIKE :language")
    LiveData<List<Movie>> searchMovies(String query, String genre, String language);

    @Query("SELECT DISTINCT genre FROM movie")

    List<String> getAllGenres();

    @Query("SELECT DISTINCT language FROM movie")
    List<String> getAllLanguages();
    }

