package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reviewmate.model.Director;

import java.util.List;

@Dao
public interface DirectorDAO {

    @Insert
    void insert(Director director);

    @Update
    void update(Director director);

    @Delete
    void delete(Director director);

    @Query("SELECT * FROM directors WHERE director_id = :directorId")
    Director getDirectorById(int directorId);

    @Query("SELECT * FROM directors")
    List<Director> getAllDirectors();
}
