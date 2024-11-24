package com.example.reviewmate.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.reviewmate.data.UserRepository;
import com.example.reviewmate.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LoginViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<Boolean> getLoginStatus() {
        return loginStatus;
    }

    public void login(String email, String password) {
        executorService.execute(() -> {
            User user = userRepository.getUserByEmailAndPassword(email, password);
            loginStatus.postValue(user != null);
        });
    }
}
