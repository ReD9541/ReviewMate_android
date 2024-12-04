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

}
