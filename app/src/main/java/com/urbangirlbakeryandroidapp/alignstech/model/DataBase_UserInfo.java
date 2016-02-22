package com.urbangirlbakeryandroidapp.alignstech.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Dell on 11/23/2015.
 */
@Table(name = "MyTable")
public class DataBase_UserInfo extends Model{

    @Column(name = "Fb_id")
    private String fb_id;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "MobileNo")
    private String mobilePrimary;

    @Column(name = "MobileNo2")
    private String mobileSecondary;

    @Column(name = "Email")
    private String email;

    @Column(name = "Dob")
    private String dob;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Zone")
    private String zone;

    @Column(name = "District")
    private String district;

    @Column(name = "Location")
    private String location;

    @Column(name = "PicUrl")
    private String profilePicUrl;

    @Column(name = "billingAddress")
    private String billingAddress;

    @Column(name = "sippingAddress")
    private String sippingAddress;

    public DataBase_UserInfo(){}

    public DataBase_UserInfo(String fb_id, String firstName, String lastName, String mobilePrimary, String email, String dob, String gender, String zone, String district, String location, String profilePicUrl , String billingAddress , String sippingAddress , String mobileSecondary) {
        this.fb_id = fb_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePrimary = mobilePrimary;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.zone = zone;
        this.district = district;
        this.location = location;
        this.profilePicUrl = profilePicUrl;
        this.billingAddress = billingAddress;
        this.sippingAddress = sippingAddress;
        this.mobileSecondary = mobileSecondary;
    }

    public DataBase_UserInfo(String fb_id, String firstName, String lastName, String mobilePrimary, String email, String dob, String gender, String zone, String district, String location, String profilePicUrl) {
        this.fb_id = fb_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePrimary = mobilePrimary;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.zone = zone;
        this.district = district;
        this.location = location;
        this.profilePicUrl = profilePicUrl;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
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

    public String getMobilePrimary() {
        return mobilePrimary;
    }

    public void setMobilePrimary(String mobilePrimary) {
        this.mobilePrimary = mobilePrimary;
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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getSippingAddress() {
        return sippingAddress;
    }

    public void setSippingAddress(String sippingAddress) {
        this.sippingAddress = sippingAddress;
    }

    public String getMobileSecondary() {
        return mobileSecondary;
    }

    public void setMobileSecondary(String mobileSecondary) {
        this.mobileSecondary = mobileSecondary;
    }
}
