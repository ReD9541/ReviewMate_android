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
import com.example.reviewmate.model.Watchlist;

import java.util.List;

@Dao
public interface WatchlistDAO {

    @Insert
    void insert(Watchlist watchlist);

    @Update
    void update(Watchlist watchlist);

    @Delete
    void delete(Watchlist watchlist);

}
//This helps me initialize the database but doesn't have any function of it for now