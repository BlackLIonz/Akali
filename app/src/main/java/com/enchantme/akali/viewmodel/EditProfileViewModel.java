package com.enchantme.akali.viewmodel;

import java.io.Serializable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditProfileViewModel extends ViewModel implements Serializable {
    private MutableLiveData<String> profileName;
    private MutableLiveData<String> nickName;
    private MutableLiveData<String> phoneNumber;
    private MutableLiveData<String> email;
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

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
        if (this.profileName == null) {
            this.profileName = new MutableLiveData<>();
        }
        this.profileName.setValue(profileName);
    }

    public void setEmail(String email) {
        if (this.email == null) {
            this.email = new MutableLiveData<>();
        }
        this.email.setValue(email);
    }

    public void setNickName(String nickName) {
        if (this.nickName == null) {
            this.nickName = new MutableLiveData<>();
        }
        this.nickName.setValue(nickName);
    }

    public void setPhoneNumber(String phoneNumber) {
        if (this.phoneNumber == null) {
            this.phoneNumber = new MutableLiveData<>();
        }
        this.phoneNumber.setValue(phoneNumber);
    }

}
