package com.enchantme.akali.dto;

import java.io.Serializable;

public class ProfileDTO implements Serializable {

    //region Variables

    private String profileName;
    private String profileNickName;
    private String profilePhone;
    private String profileEmail;
    private String imagePath;

    //endregion

    //region Constructors

    public ProfileDTO() {

    }

    public ProfileDTO(String profileName, String profileNickName, String profilePhone, String profileEmail) {
        this.setProfileName(profileName);
        this.setProfileNickName(profileNickName);
        this.setProfilePhone(profilePhone);
        this.setProfileEmail(profileEmail);
    }

    //endregion

    //region Public Methods

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileNickName() {
        return profileNickName;
    }

    public void setProfileNickName(String profileNickName) {
        this.profileNickName = profileNickName;
    }

    public String getProfilePhone() {
        return profilePhone;
    }

    public void setProfilePhone(String profilePhone) {
        this.profilePhone = profilePhone;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    //endregion
}
