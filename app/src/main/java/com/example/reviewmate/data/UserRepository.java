package com.example.reviewmate.data;

import android.app.Application;

import com.example.reviewmate.data.ReviewMateRoomDatabase;
import com.example.reviewmate.dao.UserDAO;
import com.example.reviewmate.model.User;

import java.util.concurrent.ExecutorService;

public class UserRepository {
    private final UserDAO userDAO;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        ReviewMateRoomDatabase db = ReviewMateRoomDatabase.getDatabase(application);
        userDAO = db.userDAO();
        executorService = ReviewMateRoomDatabase.databaseWriteExecutor;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return userDAO.getUserByEmailAndPassword(email, password);
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDAO.insert(user));
    }
}
