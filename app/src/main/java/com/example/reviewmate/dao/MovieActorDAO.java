package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.reviewmate.model.MovieActor;

import java.util.List;

@Dao
public interface MovieActorDAO {

    @Insert
    void insert(MovieActor movieActor);

    @Update
    void update(MovieActor movieActor);

    @Delete
    void delete(MovieActor movieActor);

    @Query("SELECT actor_id FROM movie_actors WHERE movie_id = :movieId")
    List<Integer> getActorsForMovie(int movieId);

    @Query("SELECT movie_id FROM movie_actors WHERE actor_id = :actorId")
    List<Integer> getMoviesForActor(int actorId);
}
