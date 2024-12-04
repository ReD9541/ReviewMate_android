package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reviewmate.model.Actor;

import java.util.List;

@Dao
public interface ActorDAO {

    @Insert
    void insert(Actor actor);

    @Update
    void update(Actor actor);

    @Delete
    void delete(Actor actor);

}
