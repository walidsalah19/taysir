package com.example.taysir.Models;

public class CustomerModel {
    private String CID,UserName,FullName,Email,PhoneNum,Gender,DOB,address,city,Street;
    private int NID,PostCode;

    public CustomerModel(String CID, String userName, String fullName, String email, String phoneNum, String gender, String DOB, String address, String city, String street, int NID, int postCode) {
        this.CID = CID;
        UserName = userName;
        FullName = fullName;
        Email = email;
        PhoneNum = phoneNum;
        Gender = gender;
        this.DOB = DOB;
        this.address = address;
        this.city = city;
        Street = street;
        this.NID = NID;
        PostCode = postCode;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getNID() {
        return NID;
    }

    public void setNID(int NID) {
        this.NID = NID;
    }

    public int getPostCode() {
        return PostCode;
    }

    public void setPostCode(int postCode) {
        PostCode = postCode;
    }
}
