package com.example.taysir.Models;

public class OfferModel {
    String offerId, brokerId,brokerName,clintId,orderId,orderDate;
    int totalCost,orderCost,commission;

    public OfferModel(String offerId, String brokerId, String brokerName, String clintId, String orderId, String orderDate, int totalCost, int orderCost, int commission) {
        this.offerId = offerId;
        this.brokerId = brokerId;
        this.brokerName = brokerName;
        this.clintId = clintId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.orderCost = orderCost;
        this.commission = commission;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getClintId() {
        return clintId;
    }

    public void setClintId(String clintId) {
        this.clintId = clintId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(int orderCost) {
        this.orderCost = orderCost;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }
}
