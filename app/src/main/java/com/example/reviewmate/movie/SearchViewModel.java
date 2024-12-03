package com.example.reviewmate.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.model.Movie;
import com.example.reviewmate.data.MovieRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private final MovieRepository movieRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> searchMovies(String query, String genre, String language) {
        return movieRepository.searchMovies(query, genre, language);
    }

    public LiveData<List<String>> getGenres() {
        return movieRepository.getAllGenres();
    }

    public LiveData<List<String>> getLanguages() {
        return movieRepository.getAllLanguages();
    }
}
