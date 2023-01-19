package com.example.taysir.Models;

public class RatingModel {
    String Bid,customerName,ratingText;
    int ratingNum;

    public RatingModel(String bid, String customerName, String ratingText, int ratingNum) {
        Bid = bid;
        this.customerName = customerName;
        this.ratingText = ratingText;
        this.ratingNum = ratingNum;
    }

    public String getBid() {
        return Bid;
    }

    public void setBid(String bid) {
        Bid = bid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public int getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(int ratingNum) {
        this.ratingNum = ratingNum;
    }
}
