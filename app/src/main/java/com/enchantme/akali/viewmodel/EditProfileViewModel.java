package com.enchantme.akali.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditProfileViewModel extends ViewModel {
    private MutableLiveData<String> profileName;
    private MutableLiveData<String> nickName;
    private MutableLiveData<String> phoneNumber;
    private MutableLiveData<String> email;

    public LiveData<String> getProfileName() {
        if (profileName == null) {
            profileName = new MutableLiveData<>();
            profileName.setValue("Ilya NeDziamidovich");
        }
        return profileName;
    }

    public LiveData<String> getEmail() {
        if (email == null) {
            email = new MutableLiveData<>();
            email.setValue("melamory39@gmail.com");
        }
        return email;
    }

    public LiveData<String> getPhoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new MutableLiveData<>();
            phoneNumber.setValue("+375333934351");
        }
        return phoneNumber;
    }

    public LiveData<String> getNickname() {
        if (nickName == null) {
            nickName = new MutableLiveData<>();
            nickName.setValue("BlackRock");
        }
        return nickName;
    }

    public void setProfileName(String profileName) {
        this.profileName.setValue(profileName);
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    private void setNickName(String nickName) {
        this.nickName.setValue(nickName);
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setValue(phoneNumber);
    }

}
