package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.reviewmate.model.MovieDirector;

import java.util.List;

@Dao
public interface MovieDirectorDAO {

    @Insert
    void insert(MovieDirector movieDirector);

    @Query("SELECT director_id FROM movie_directors WHERE movie_id = :movieId")
    List<Integer> getDirectorsForMovie(int movieId);
}
