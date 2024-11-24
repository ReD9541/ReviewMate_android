package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.reviewmate.model.MovieDirector;

import java.util.List;

@Dao
public interface MovieDirectorDAO {

    @Insert
    void insert(MovieDirector movieDirector);

    @Update
    void update(MovieDirector movieDirector);

    @Delete
    void delete(MovieDirector movieDirector);

    @Query("SELECT director_id FROM movie_directors WHERE movie_id = :movieId")
    List<Integer> getDirectorsForMovie(int movieId);

    @Query("SELECT movie_id FROM movie_directors WHERE director_id = :directorId")
    List<Integer> getMoviesForDirector(int directorId);
}
