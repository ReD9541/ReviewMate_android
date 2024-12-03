package com.example.reviewmate.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.MovieRepository;
import com.example.reviewmate.model.Movie;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MovieRepository movieRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return movieRepository.getTopRatedMovies();
    }

    public LiveData<List<Movie>> getLatestMovies() {
        return movieRepository.getLatestMovies();
    }
}
