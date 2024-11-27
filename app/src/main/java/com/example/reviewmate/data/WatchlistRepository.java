package com.example.reviewmate.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.reviewmate.dao.WatchlistDAO;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Watchlist;

import java.util.List;

public class WatchlistRepository {
    private final WatchlistDAO watchlistDAO;

    public WatchlistRepository(Application application) {
        ReviewMateRoomDatabase db = ReviewMateRoomDatabase.getDatabase(application);
        watchlistDAO = db.watchlistDAO();
    }

    public void addToWatchlist(Watchlist watchlist) {
        ReviewMateRoomDatabase.databaseWriteExecutor.execute(() -> watchlistDAO.addToWatchlist(watchlist));
    }

    public void removeFromWatchlist(int userId, int movieId) {
        ReviewMateRoomDatabase.databaseWriteExecutor.execute(() -> watchlistDAO.removeFromWatchlist(userId, movieId));
    }

    public LiveData<List<Movie>> getMoviesInWatchlist(int userId) {
        return watchlistDAO.getMoviesInWatchlist(userId);
    }
}

