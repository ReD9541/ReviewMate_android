package com.example.reviewmate.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.reviewmate.dao.MoviesWatchedDAO;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.MoviesWatched;

import java.util.List;

public class MoviesWatchedRepository {
    private final MoviesWatchedDAO moviesWatchedDAO;

    public MoviesWatchedRepository(Application application) {
        ReviewMateRoomDatabase db = ReviewMateRoomDatabase.getDatabase(application);
        moviesWatchedDAO = db.moviesWatchedDAO();
    }

    public void addMovieToWatchedList(MoviesWatched moviesWatched) {
        ReviewMateRoomDatabase.databaseWriteExecutor.execute(() -> moviesWatchedDAO.addMovieToWatchedList(moviesWatched));
    }

    public void removeMovieFromWatchedList(int userId, int movieId) {
        ReviewMateRoomDatabase.databaseWriteExecutor.execute(() -> moviesWatchedDAO.removeMovieFromWatchedList(userId, movieId));
    }

    public LiveData<List<Movie>> getMoviesWatchedByUserId(int userId) {
        return moviesWatchedDAO.getMoviesWatchedByUserId(userId);
    }
}

