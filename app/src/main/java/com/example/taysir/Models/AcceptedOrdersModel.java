package com.example.taysir.Models;

import java.util.ArrayList;

public class AcceptedOrdersModel extends NewOrderModel {
    String BrokerId,Rating;
    float TotalCost;
    public AcceptedOrdersModel(String webSitLink, String webSitName, String clintId, String clintName, String clintLocation, String orderId, String orderStat, String orderDate, int orderNum, ArrayList<OrderDetailsModel> orderDetails, String brokerId, float totalCost, String rating) {
        super(webSitLink, webSitName, clintId, clintName, clintLocation, orderId, orderStat, orderDate, orderNum, orderDetails);
        BrokerId = brokerId;
        TotalCost = totalCost;
        Rating = rating;
    }

    public String getBrokerId() {
        return BrokerId;
    }

    public void setBrokerId(String brokerId) {
        BrokerId = brokerId;
    }

    public float getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(float totalCost) {
        TotalCost = totalCost;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
