package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM movies_watched")
    List<MoviesWatched> getAllMoviesWatched();
}
