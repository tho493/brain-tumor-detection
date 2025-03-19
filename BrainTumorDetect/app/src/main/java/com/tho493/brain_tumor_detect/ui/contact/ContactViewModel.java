package com.tho493.brain_tumor_detect.ui.contact;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactViewModel extends ViewModel {

    private final MutableLiveData<String> title;
    private final MutableLiveData<String> address;
    private final MutableLiveData<String> phone;
    private final MutableLiveData<String> email;

    public ContactViewModel() {
        title = new MutableLiveData<>();
        title.setValue("Liên hệ với chúng tôi");

        address = new MutableLiveData<>();
        address.setValue("56 17 Kim Đồng, Phường Sao Đỏ, Thành phố Chí Linh, Tỉnh Hải Dương");

        phone = new MutableLiveData<>();
        phone.setValue("(+84) 0896 505 169");

        email = new MutableLiveData<>();
        email.setValue("chitho040903@gmail.com");
    }

    public ContactViewModel(MutableLiveData<String> title, MutableLiveData<String> address, MutableLiveData<String> phone, MutableLiveData<String> email) {
        this.title = title;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getAddress() {
        return address;
    }

    public LiveData<String> getPhone() {
        return phone;
    }

    public LiveData<String> getEmail() {
        return email;
    }
}