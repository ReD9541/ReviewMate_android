package com.example.reviewmate.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.reviewmate.data.UserRepository;
import com.example.reviewmate.model.User;
import com.example.reviewmate.model.Userinfo;

public class RegisterViewModel extends AndroidViewModel {

    private final UserRepository repository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void registerUser(User user, Userinfo userinfo) {
        repository.insertUserWithUserinfo(user, userinfo);
    }


    public void updateUser(User user, Userinfo userinfo) {
        repository.updateUserWithUserinfo(user, userinfo);
    }

    public void deleteUser(User user, Userinfo userinfo) {
        repository.deleteUserWithUserinfo(user, userinfo);
    }
}
