package com.example.reviewmate.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.WatchlistRepository;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Watchlist;

import java.util.List;

public class WatchlistViewModel extends AndroidViewModel {
    private final WatchlistRepository repository;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        repository = new WatchlistRepository(application);
    }

    public void addToWatchlist(Watchlist watchlist) {
        repository.addToWatchlist(watchlist);
    }

    public void removeFromWatchlist(int userId, int movieId) {
        repository.removeFromWatchlist(userId, movieId);
    }

    public LiveData<List<Movie>> getMoviesInWatchlist(int userId) {
        return repository.getMoviesInWatchlist(userId);
    }
}
