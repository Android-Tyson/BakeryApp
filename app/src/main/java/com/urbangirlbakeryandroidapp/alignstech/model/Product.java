package com.urbangirlbakeryandroidapp.alignstech.model;

/**
 * Created by Dell on 1/7/2016.
 */
public class Product {

    private String productName , productImageUrl;

    public Product(){ }

    public Product(String productName, String productUrl) {
        this.productName = productName;
        this.productImageUrl = productUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
