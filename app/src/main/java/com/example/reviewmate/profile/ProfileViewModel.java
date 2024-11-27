package com.example.reviewmate.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.UserRepository;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Review;
import com.example.reviewmate.model.Userinfo;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<Userinfo> getUserInfo(int userId) {
        return userRepository.getUserinfoByUserId(userId);
    }

    public LiveData<List<Movie>> getMoviesWatched(int userId) {
        return userRepository.getMoviesWatchedByUserId(userId);
    }
    public LiveData<List<Movie>> getMoviesWatchlisted(int userId) {
        return userRepository.getMoviesWatchlistedByUserID(userId);
    }

    public LiveData<List<Review>> getUserReviews(int userId) {
        return userRepository.getUserReviewsByUserId(userId);
    }
}
