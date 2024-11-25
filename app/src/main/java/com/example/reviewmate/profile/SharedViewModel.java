package com.example.reviewmate.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Integer> userId = new MutableLiveData<>(null);


    public void setUserId(int id) {
        userId.setValue(id);
    }

    public LiveData<Integer> getUserId() {
        return userId;
    }

    public void clearUserId() {
        userId.setValue(null);
    }
}
