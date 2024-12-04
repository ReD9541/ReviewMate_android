package com.example.reviewmate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.MoviesWatched;

import java.util.List;

@Dao
public interface MoviesWatchedDAO {

    @Insert
    void insert(MoviesWatched moviesWatched);

    @Update
    void update(MoviesWatched moviesWatched);

    @Delete
    void delete(MoviesWatched moviesWatched);

    @Query("SELECT * FROM movies_watched WHERE user_id = :userId")
    List<MoviesWatched> getMoviesWatchedByUser(int userId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovieToWatchedList(MoviesWatched moviesWatched);

    @Query("DELETE FROM movies_watched WHERE user_id = :userId AND movie_id = :movieId")
    void removeMovieFromWatchedList(int userId, int movieId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM movie INNER JOIN movies_watched ON movie.movie_id = movies_watched.movie_id WHERE movies_watched.user_id = :userId")
    LiveData<List<Movie>> getMoviesWatchedByUserId(int userId);
}
