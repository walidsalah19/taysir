package com.app.taysir.Models;

public class BrokerModel {
    private String BID,UserName,FullName,Email,PhoneNum,Gender,Status,DOB,FreeWorkDocumentCode;
    private int NID,MaroOfNum;

    public BrokerModel(String BID, String userName, String fullName, String email, String phoneNum, String gender, String status, String DOB, int NID, int maroOfNum, String freeWorkDocumentCode) {
        this.BID = BID;
        UserName = userName;
        FullName = fullName;
        Email = email;
        PhoneNum = phoneNum;
        Gender = gender;
        Status = status;
        this.DOB = DOB;
        this.NID = NID;
        MaroOfNum = maroOfNum;
        FreeWorkDocumentCode = freeWorkDocumentCode;
    }

    public String getBID() {
        return BID;
    }

    public void setBID(String BID) {
        this.BID = BID;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getNID() {
        return NID;
    }

    public void setNID(int NID) {
        this.NID = NID;
    }

    public int getMaroOfNum() {
        return MaroOfNum;
    }

    public void setMaroOfNum(int maroOfNum) {
        MaroOfNum = maroOfNum;
    }

    public String getFreeWorkDocumentCode() {
        return FreeWorkDocumentCode;
    }

    public void setFreeWorkDocumentCode(String freeWorkDocumentCode) {
        FreeWorkDocumentCode = freeWorkDocumentCode;
    }
}
