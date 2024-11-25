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

    public User getUserByEmailAndPassword(String email, String password) {
        return userDAO.getUserByEmailAndPassword(email, password);
    }

    public LiveData<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDAO.insert(user));
    }

    public void updateUser(User user) {
        executorService.execute(() -> userDAO.update(user));
    }

    public void deleteUser(User user) {
        executorService.execute(() -> userDAO.delete(user));
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
}
