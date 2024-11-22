package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM watchlist WHERE user_id = :userId")
    List<Watchlist> getWatchlistByUser(int userId);

    @Query("SELECT * FROM watchlist")
    List<Watchlist> getAllWatchlist();
}
