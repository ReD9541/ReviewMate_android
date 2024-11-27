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

    @Query("SELECT * FROM watchlist WHERE user_id = :userId")
    List<Watchlist> getWatchlistByUser(int userId);

    @Query("SELECT * FROM watchlist")
    List<Watchlist> getAllWatchlist();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToWatchlist(Watchlist watchlist);

    @Query("DELETE FROM watchlist WHERE user_id = :userId AND movie_id = :movieId")
    void removeFromWatchlist(int userId, int movieId);

    @Query("SELECT * FROM watchlist WHERE user_id = :userId")
    LiveData<List<Watchlist>> getWatchlistByUserId(int userId);
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM movie INNER JOIN watchlist ON movie.movie_id = watchlist.movie_id WHERE watchlist.user_id = :userId")
    LiveData<List<Movie>> getMoviesInWatchlist(int userId);
}
