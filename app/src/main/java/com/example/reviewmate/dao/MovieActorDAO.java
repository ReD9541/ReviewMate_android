package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.reviewmate.model.MovieActor;

import java.util.List;

@Dao
public interface MovieActorDAO {

    @Insert
    void insert(MovieActor movieActor);

    @Query("SELECT actor_id FROM movie_actors WHERE movie_id = :movieId")
    List<Integer> getActorsForMovie(int movieId);
}
