package com.enchantme.akali.viewmodel;

import android.graphics.drawable.Drawable;
import android.media.Image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel  extends ViewModel {

    //region Variables

    private MutableLiveData<String> profileName;
    private MutableLiveData<String> profilePhoneNumber;
    private MutableLiveData<String> profileEmail;
    private MutableLiveData<Drawable> profileImage;

    //endregion

    //region Constructors

    public ProfileViewModel() {
        profileName = new MutableLiveData<>();
        profilePhoneNumber = new MutableLiveData<>();
        profileEmail = new MutableLiveData<>();
        profileImage = new MutableLiveData<>();
    }

    //endregion

    //region Public Methods

    public LiveData<String> getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName.setValue(profileName);
    }

    public LiveData<String> getProfilePhoneNumber() {
        return profilePhoneNumber;
    }

    public void setProfilePhoneNumber(String profilePhoneNumber) {
        this.profilePhoneNumber.setValue(profilePhoneNumber);
    }

    public LiveData<String> getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail.setValue(profileEmail);
    }

    public LiveData<Drawable> getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Drawable profileImage) {
        this.profileImage.setValue(profileImage);
    }

    //endregion

}
