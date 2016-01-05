package com.urbangirlbakeryandroidapp.alignstech.model;

/**
 * Created by Dell on 1/5/2016.
 */
public class NormalRegisterModel {

    private String fb_id;
    private String fullName;
    private String mobileNo;
    private String email;
    private String dob;
    private String gender;
    private String zone;
    private String district;
    private String location;

    public NormalRegisterModel(){}

    public NormalRegisterModel(String fb_id, String fullName, String mobileNo, String email, String dob, String gender, String zone, String district, String location) {
        this.fb_id = fb_id;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.zone = zone;
        this.district = district;
        this.location = location;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
