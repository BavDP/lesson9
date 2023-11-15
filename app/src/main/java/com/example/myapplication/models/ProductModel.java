package com.example.myapplication.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private String productName;
    private double productPrice;
    private String code;

    public ProductModel(String code, String productName, double productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
