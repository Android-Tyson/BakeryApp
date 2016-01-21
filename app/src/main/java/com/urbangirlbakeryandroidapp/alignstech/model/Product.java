package com.urbangirlbakeryandroidapp.alignstech.model;

/**
 * Created by Dell on 1/7/2016.
 */
public class Product {

    private String product_id, productName , productImageUrl;

    public Product(){ }

    public Product(String product_id , String productName, String productUrl) {
        this.product_id = product_id;
        this.productName = productName;
        this.productImageUrl = productUrl;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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
