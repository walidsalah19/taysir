package com.app.taysir.Models;

import java.util.ArrayList;

public class NewOrderModel {
    String WebSitLink,WebSitName,clintId,clintName,clintLocation,orderId,orderStat,OrderDate;
    int orderNum;
    ArrayList<OrderDetailsModel> OrderDetails;

    public NewOrderModel(String webSitLink, String webSitName, String clintId, String clintName, String clintLocation, String orderId, String orderStat, String orderDate, int orderNum, ArrayList<OrderDetailsModel> orderDetails) {
        WebSitLink = webSitLink;
        WebSitName = webSitName;
        this.clintId = clintId;
        this.clintName = clintName;
        this.clintLocation = clintLocation;
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

    public String getClintId() {
        return clintId;
    }

    public void setClintId(String clintId) {
        this.clintId = clintId;
    }

    public String getClintName() {
        return clintName;
    }

    public void setClintName(String clintName) {
        this.clintName = clintName;
    }

    public String getClintLocation() {
        return clintLocation;
    }

    public void setClintLocation(String clintLocation) {
        this.clintLocation = clintLocation;
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
