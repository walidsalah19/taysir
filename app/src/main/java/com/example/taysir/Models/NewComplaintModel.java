package com.example.taysir.Models;

public class NewComplaintModel {
    private String UserName,Inquire,InquireId,UserId;
    private int InquireNum;

    public NewComplaintModel(String userName, String inquire, String inquireId, String userId, int inquireNum) {
        UserName = userName;
        Inquire = inquire;
        InquireId = inquireId;
        UserId = userId;
        InquireNum = inquireNum;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getInquire() {
        return Inquire;
    }

    public void setInquire(String inquire) {
        Inquire = inquire;
    }

    public String getInquireId() {
        return InquireId;
    }

    public void setInquireId(String inquireId) {
        InquireId = inquireId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getInquireNum() {
        return InquireNum;
    }

    public void setInquireNum(int inquireNum) {
        InquireNum = inquireNum;
    }
}
