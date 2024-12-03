package com.example.reviewmate.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.reviewmate.dao.MovieDAO;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Watchlist;
import com.example.reviewmate.model.MoviesWatched;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    public LiveData<Movie> getMovieDetails(int movieId) {
        return movieDAO.getMovieDetails(movieId);
    }

    public void addToWatchlist(int userId, int movieId) {
        executorService.execute(() -> {
            String currentDate = getCurrentDate();
            Watchlist watchlist = new Watchlist(userId, movieId, currentDate);
            movieDAO.addToWatchlist(watchlist);
        });
    }

    public void removeFromWatchlist(int userId, int movieId) {
        executorService.execute(() -> movieDAO.removeFromWatchlist(movieId, userId));
    }

    public void addToWatchedList(int userId, int movieId) {
        executorService.execute(() -> {
            String currentDate = getCurrentDate();
            MoviesWatched moviesWatched = new MoviesWatched(userId, movieId, currentDate);
            movieDAO.addToWatchedList(moviesWatched);
        });
    }

    public void removeFromWatchedList(int userId, int movieId) {
        executorService.execute(() -> movieDAO.removeFromWatchedList(movieId, userId));
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public LiveData<List<String>> getAllGenres() {
        return movieDAO.getAllGenres();
    }

    public LiveData<List<String>> getAllLanguages() {
        return movieDAO.getAllLanguages();
    }

    public LiveData<List<Movie>> searchMovies(String query, String genre, String language) {
        query = "%" + query + "%";

        if (genre.isEmpty() && language.isEmpty()) {
            return movieDAO.searchMoviesByName(query);
        } else if (genre.isEmpty()) {
            return movieDAO.searchMoviesByNameAndLanguage(query, language);
        } else if (language.isEmpty()) {
            return movieDAO.searchMoviesByNameAndGenre(query, genre);
        } else {
            return movieDAO.searchMoviesByAllFilters(query, genre, language);
        }

    }

    public LiveData<Boolean> isMovieInWatchlist(int userId, int movieId) {
        return movieDAO.isMovieInWatchlist(userId, movieId);
    }

    public LiveData<Boolean> isMovieInWatchedList(int userId, int movieId) {
        return movieDAO.isMovieInWatchedList(userId, movieId);
    }

    public LiveData<Boolean> isInWatchlist(int userId, int movieId) {
        return movieDAO.isInWatchlist(userId, movieId);
    }

    public LiveData<Boolean> isInWatchedList(int userId, int movieId) {
        return movieDAO.isInWatchedList(userId, movieId);
    }

    public LiveData<Boolean> hasReviewed(int userId, int movieId) {
        return movieDAO.hasReviewed(userId, movieId);
    }


}
