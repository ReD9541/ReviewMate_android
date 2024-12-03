package com.example.reviewmate.data;

import static com.example.reviewmate.data.ReviewMateRoomDatabase.databaseWriteExecutor;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.reviewmate.dao.MovieDAO;
import com.example.reviewmate.dao.ReviewsDAO;
import com.example.reviewmate.dao.UserDAO;
import com.example.reviewmate.dao.UserinfoDAO;
import com.example.reviewmate.model.Movie;
import com.example.reviewmate.model.Review;
import com.example.reviewmate.model.User;
import com.example.reviewmate.model.Userinfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class UserRepository {
    private final UserDAO userDAO;
    private final UserinfoDAO userinfoDAO;
    private final MovieDAO movieDAO;
    private final ReviewsDAO reviewsDAO;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        ReviewMateRoomDatabase db = ReviewMateRoomDatabase.getDatabase(application);
        userDAO = db.userDAO();
        userinfoDAO = db.userinfoDAO();
        movieDAO = db.movieDAO();
        reviewsDAO = db.reviewsDAO();
        executorService = databaseWriteExecutor;
    }

    // Insert user and userinfo
    public void insertUserWithUserinfo(User user, Userinfo userinfo) {
        executorService.execute(() -> {
            long userId = userDAO.insert(user);
            userinfo.setUserId((int) userId);
            userinfoDAO.insert(userinfo);
        });
    }

    public void updateUserWithUserinfo(User user, Userinfo userinfo) {
        executorService.execute(() -> {
            userDAO.update(user);
            userinfoDAO.update(userinfo);
        });
    }

    public void deleteUserWithUserinfo(User user, Userinfo userinfo) {
        executorService.execute(() -> {
            userDAO.delete(user);
            userinfoDAO.delete(userinfo);
        });
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return userDAO.getUserByEmailAndPassword(email, password);
    }

    public LiveData<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public LiveData<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public LiveData<Userinfo> getUserinfoByUserId(int userId) {
        return userinfoDAO.getUserinfoByUserId(userId);
    }

    public void insertUserinfo(Userinfo userinfo) {
        executorService.execute(() -> userinfoDAO.insert(userinfo));
    }

    public void updateUserinfo(Userinfo userinfo) {
        executorService.execute(() -> userinfoDAO.update(userinfo));
    }

    public void deleteUserinfo(Userinfo userinfo) {
        executorService.execute(() -> userinfoDAO.delete(userinfo));
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDAO.insert(user));
    }

    public void deleteUser(User user) {
        executorService.execute(() -> userDAO.delete(user));
    }

    public void updateUser(User user) {
        executorService.execute(() -> userDAO.update(user));
    }

    public LiveData<List<Movie>> getMoviesWatchedByUserId(int userId) {
        return movieDAO.getMoviesWatchedByUserId(userId);
    }

    public LiveData<List<Movie>> getMoviesWatchlistedByUserID(int userid) {
        return movieDAO.getMoviesWatchlistedByUserId(userid);
    }

    public LiveData<List<Review>> getUserReviewsWithMovieNamesByUserId(int userId) {
        return reviewsDAO.getUserReviewsWithMovieNamesByUserId(userId);
    }

    public LiveData<List<String>> getMovienamesByUserId(int user_id) {
        return reviewsDAO.getMovienamesByUserId(user_id);
    }

    public LiveData<Boolean> updateUserProfile(int userId, String firstName, String lastName, String bio, String address) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        executorService.execute(() -> {
            int rowsUpdated = userDAO.updateUserProfile(userId, firstName, lastName, bio, address);
            result.postValue(rowsUpdated > 0);
        });
        return result;
    }

    public LiveData<Boolean> updateUserPassword(int userId, String newPassword) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        executorService.execute(() -> {
            int rowsUpdated = userDAO.updateUserPassword(userId, newPassword);
            result.postValue(rowsUpdated > 0);
        });
        return result;
    }
}
