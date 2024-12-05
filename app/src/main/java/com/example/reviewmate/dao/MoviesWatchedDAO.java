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

}
//This helps me initialize the database but doesn't have any function of it for now