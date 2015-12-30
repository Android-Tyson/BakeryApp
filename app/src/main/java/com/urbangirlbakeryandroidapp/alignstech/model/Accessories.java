package com.urbangirlbakeryandroidapp.alignstech.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Dell on 12/28/2015.
 */

@Table(name = "Accessories")
public class Accessories extends Model{

    @Column(name = "Category")
    private String categoryName;

    public Accessories(){ }

    public Accessories(String categoryName){
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}