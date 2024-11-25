package com.example.reviewmate.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.reviewmate.dao.MovieDAO;
import com.example.reviewmate.model.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MovieRepository {

    private final MovieDAO movieDAO;
    private final ExecutorService executorService;

    public MovieRepository(Application application) {
        ReviewMateRoomDatabase db = ReviewMateRoomDatabase.getDatabase(application);
        movieDAO = db.movieDAO();
        executorService = ReviewMateRoomDatabase.databaseWriteExecutor;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return movieDAO.getTopRatedMovies();
    }

    public LiveData<List<Movie>> getLatestMovies() {
        return movieDAO.getLatestMovies();
    }
}
