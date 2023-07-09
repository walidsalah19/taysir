package com.app.taysir.Models;

public class OrderDetailsModel {
    String productLink,productColor,productPhoto,productNotes;
    int productQuantity,productCode,productSize;
    float ProductCost;

    public OrderDetailsModel(String productLink, String productColor, String productPhoto, String productNotes, int productQuantity, int productCode, int productSize, float productCost) {
        this.productLink = productLink;
        this.productColor = productColor;
        this.productPhoto = productPhoto;
        this.productNotes = productNotes;
        this.productQuantity = productQuantity;
        this.productCode = productCode;
        this.productSize = productSize;
        ProductCost = productCost;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public String getProductNotes() {
        return productNotes;
    }

    public void setProductNotes(String productNotes) {
        this.productNotes = productNotes;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public float getProductCost() {
        return ProductCost;
    }

    public void setProductCost(float productCost) {
        ProductCost = productCost;
    }
}
