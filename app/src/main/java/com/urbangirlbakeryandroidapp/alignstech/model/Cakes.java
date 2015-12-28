package com.urbangirlbakeryandroidapp.alignstech.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Dell on 12/28/2015.
 */

@Table(name = "Cakes")
public class Cakes extends Model{

    @Column(name = "Category" , onUniqueConflict = Column.ConflictAction.REPLACE)
    private String categoryName;

    public Cakes(){ }

    public Cakes(String categoryName){
        this.categoryName = categoryName;
    }

}
