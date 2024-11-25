package com.example.reviewmate.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.reviewmate.data.UserRepository;
import com.example.reviewmate.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final LiveData<List<User>> allUsers;
    private final MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> loggedInUserId = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public LiveData<Boolean> getLoginStatus() {
        return loginStatus;
    }

    public LiveData<Integer> getLoggedInUserId() {
        return loggedInUserId;
    }

    public void login(String email, String password) {
        final String normalizedEmail = email != null ? email.trim().toLowerCase() : null;
        final String trimmedPassword = password != null ? password.trim() : null;

        executorService.execute(() -> {
            User user = userRepository.getUserByEmailAndPassword(normalizedEmail, trimmedPassword);
            if (user != null) {
                loggedInUserId.postValue(user.getId());
                loginStatus.postValue(true);
            } else {
                loginStatus.postValue(false);
            }
        });
    }

    public void clearLoginStatus() {
        loginStatus.postValue(null);
        loggedInUserId.postValue(null);
    }

    public void insert(User user) {
        executorService.execute(() -> userRepository.insertUser(user));
    }

    public void update(User user) {
        executorService.execute(() -> userRepository.updateUser(user));
    }

    public void delete(User user) {
        executorService.execute(() -> userRepository.deleteUser(user));
    }

    public LiveData<User> findById(String email) {
        return userRepository.findByEmail(email);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> checkLogin(String email, String password) {
        final String normalizedEmail = email != null ? email.trim().toLowerCase() : null;
        final String trimmedPassword = password != null ? password.trim() : null;

        MutableLiveData<User> result = new MutableLiveData<>();
        executorService.execute(() -> {
            User user = userRepository.getUserByEmailAndPassword(normalizedEmail, trimmedPassword);
            result.postValue(user);
        });
        return result;
    }
}
