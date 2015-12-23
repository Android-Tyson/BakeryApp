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
    private String mobileNo;

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

    public DataBase_UserInfo(){}

    public DataBase_UserInfo(String fb_id, String firstName, String lastName, String mobileNo, String email, String dob, String gender, String zone, String district, String location) {
        this.fb_id = fb_id;
        this.firstName = firstName;
        this.lastName = lastName;
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
