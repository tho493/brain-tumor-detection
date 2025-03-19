package com.tho493.brain_tumor_detect.ui.instruct;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InstructViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InstructViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Đây là trang giới thiệu dự án");
    }

    public LiveData<String> getText() {
        return mText;
    }
}