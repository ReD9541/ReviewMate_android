package com.example.reviewmate.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reviewmate.data.UserRepository;
import com.example.reviewmate.model.Userinfo;

public class ProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<Userinfo> getUserInfo(int userId) {
        return userRepository.getUserinfoByUserId(userId);
    }
}
