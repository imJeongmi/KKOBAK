package com.example.kkobak.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("진행 중인 챌린지 및 정보 확인");
    }

    public LiveData<String> getText() {
        return mText;
    }
}