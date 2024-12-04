package com.example.reviewmate.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;


import com.example.reviewmate.data.UserRepository;
import com.example.reviewmate.model.User;

public class SharedViewModel extends AndroidViewModel {
    public SharedViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    private final MutableLiveData<Integer> userId = new MutableLiveData<>(null);
    private final UserRepository userRepository;


    public void setUserId(int id) {
        userId.setValue(id);
    }

    public LiveData<Integer> getUserId() {
        return userId;
    }

    public void clearUserId() {
        userId.setValue(null);
    }

    public LiveData<Boolean> updateUserProfile(int userId, String firstName, String lastName, String bio, String address) {
        return userRepository.updateUserProfile(userId, firstName, lastName, bio, address);
    }

    public LiveData<Boolean> updateUserPassword(int userId, String newPassword) {
        return userRepository.updateUserPassword(userId, newPassword);
    }
}
