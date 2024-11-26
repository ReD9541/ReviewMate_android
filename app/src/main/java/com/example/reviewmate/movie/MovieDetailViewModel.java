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
}
