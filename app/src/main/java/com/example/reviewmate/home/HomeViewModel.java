package com.example.reviewmate.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.MovieRepository;
import com.example.reviewmate.model.Movie;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MovieRepository movieRepository;
    private final LiveData<List<Movie>> topRatedMovies;
    private final LiveData<List<Movie>> latestMovies;

    public HomeViewModel(Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        topRatedMovies = movieRepository.getTopRatedMovies();
        latestMovies = movieRepository.getLatestMovies();
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public LiveData<List<Movie>> getLatestMovies() {
        return latestMovies;
    }
}
