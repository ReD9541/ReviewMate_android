package com.example.reviewmate.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.MoviesWatchedRepository;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.MoviesWatched;

import java.util.List;

public class MoviesWatchedViewModel extends AndroidViewModel {
    private final MoviesWatchedRepository repository;

    public MoviesWatchedViewModel(@NonNull Application application) {
        super(application);
        repository = new MoviesWatchedRepository(application);
    }

    public void addMovieToWatchedList(MoviesWatched moviesWatched) {
        repository.addMovieToWatchedList(moviesWatched);
    }

    public void removeMovieFromWatchedList(int userId, int movieId) {
        repository.removeMovieFromWatchedList(userId, movieId);
    }

    public LiveData<List<Movie>> getMoviesWatchedByUserId(int userId) {
        return repository.getMoviesWatchedByUserId(userId);
    }
}

