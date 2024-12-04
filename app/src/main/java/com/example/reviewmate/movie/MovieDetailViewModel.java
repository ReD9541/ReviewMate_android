package com.example.reviewmate.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.MovieRepository;
import com.example.reviewmate.model.Movie;

public class MovieDetailViewModel extends AndroidViewModel {
    private final MovieRepository movieRepository;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<Movie> getMovieDetails(int movieId) {
        return movieRepository.getMovieDetails(movieId);
    }


    public void removeFromWatchlist(int userId, int movieId) {
        movieRepository.removeFromWatchlist(userId, movieId);
    }


    public void removeFromWatchedList(int userId, int movieId) {
        movieRepository.removeFromWatchedList(userId, movieId);
    }

    public LiveData<Boolean> isMovieInWatchlist(int userId, int movieId) {
        return movieRepository.isMovieInWatchlist(userId, movieId);
    }

    public LiveData<Boolean> isMovieInWatchedList(int userId, int movieId) {
        return movieRepository.isMovieInWatchedList(userId, movieId);
    }

    public LiveData<Boolean> hasReviewed(int userId, int movieId) {
        return movieRepository.hasReviewed(userId, movieId);
    }

    public void addToWatchlist(int userId, int movieId) {
        movieRepository.addToWatchlist(userId, movieId);
    }

    public void addToWatchedList(int userId, int movieId) {
        movieRepository.addToWatchedList(userId, movieId);
    }

}
