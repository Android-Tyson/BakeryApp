package com.urbangirlbakeryandroidapp.alignstech.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Dell on 12/28/2015.
 */

@Table(name = "Offers")
public class Offers extends Model{

    @Column(name = "Category")
    private String categoryName;

    public Offers(String categoryName){
        this.categoryName = categoryName;
    }

}
