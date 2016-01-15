package com.urbangirlbakeryandroidapp.alignstech.model;

/**
 * Created by Dell on 1/7/2016.
 */
public class SingleChildItem {

    private String name;

    public SingleChildItem(){ }

    public SingleChildItem(String productName) {
        this.name = productName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
