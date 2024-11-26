package com.example.reviewmate.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.reviewmate.dao.UserDAO;
import com.example.reviewmate.dao.UserinfoDAO;
import com.example.reviewmate.model.User;
import com.example.reviewmate.model.Userinfo;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class UserRepository {
    private final UserDAO userDAO;
    private final UserinfoDAO userinfoDAO;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        ReviewMateRoomDatabase db = ReviewMateRoomDatabase.getDatabase(application);
        userDAO = db.userDAO();
        userinfoDAO = db.userinfoDAO();
        executorService = ReviewMateRoomDatabase.databaseWriteExecutor;
    }

    // Insert user and userinfo
    public void insertUserWithUserinfo(User user, Userinfo userinfo) {
        executorService.execute(() -> {
            // Insert User and retrieve its ID
            userDAO.insert(user);
            Integer userId = user.getId(); // Assuming Room auto-generates the ID

            // Update userinfo with the generated user ID
            userinfo.setUserId(userId);
            userinfoDAO.insert(userinfo);
        });
    }

    // Update user and userinfo
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

    // Single Userinfo Operations
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

}
