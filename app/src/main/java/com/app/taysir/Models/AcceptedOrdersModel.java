package com.app.taysir.Models;

import java.util.ArrayList;

public class AcceptedOrdersModel extends NewOrderModel {
    String BrokerId,Rating,brokerName;
    float TotalCost;

    public AcceptedOrdersModel(String webSitLink, String webSitName, String clintId, String clintName, String clintLocation, String orderId, String orderStat, String orderDate, int orderNum, ArrayList<OrderDetailsModel> orderDetails, String brokerId, String rating, String brokerName, float totalCost) {
        super(webSitLink, webSitName, clintId, clintName, clintLocation, orderId, orderStat, orderDate, orderNum, orderDetails);
        BrokerId = brokerId;
        Rating = rating;
        this.brokerName = brokerName;
        TotalCost = totalCost;
    }

    public String getBrokerId() {
        return BrokerId;
    }

    public void setBrokerId(String brokerId) {
        BrokerId = brokerId;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public float getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(float totalCost) {
        TotalCost = totalCost;
    }
}
