package com.urbangirlbakeryandroidapp.alignstech.model;

/**
 * Created by Dell on 12/22/2015.
 */
public class UserDetials {

    private String user_id;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String phoneNumber;
    private String email;
    private String deviceId;
    private String userPictureUrl;

    public UserDetials() {
    }

    public UserDetials(String user_id, String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String deviceId, String userPictureUrl) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deviceId = deviceId;
        this.userPictureUrl = userPictureUrl;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }
}
