package com.example.taysir.Models;

import java.util.ArrayList;

public class OrderModel {
    String WebSitLink,WebSitName,userId,userName,orderId,orderStat,OrderDate;
    int orderNum;
    ArrayList<OrderDetailsModel> OrderDetails;

    public OrderModel(String webSitLink, String webSitName, String userId, String userName, String orderId, String orderStat, String orderDate, int orderNum, ArrayList<OrderDetailsModel> orderDetails) {
        WebSitLink = webSitLink;
        WebSitName = webSitName;
        this.userId = userId;
        this.userName = userName;
        this.orderId = orderId;
        this.orderStat = orderStat;
        OrderDate = orderDate;
        this.orderNum = orderNum;
        OrderDetails = orderDetails;
    }

    public String getWebSitLink() {
        return WebSitLink;
    }

    public void setWebSitLink(String webSitLink) {
        WebSitLink = webSitLink;
    }

    public String getWebSitName() {
        return WebSitName;
    }

    public void setWebSitName(String webSitName) {
        WebSitName = webSitName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public ArrayList<OrderDetailsModel> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetailsModel> orderDetails) {
        OrderDetails = orderDetails;
    }
}
