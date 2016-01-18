package com.urbangirlbakeryandroidapp.alignstech.bus;

import com.urbangirlbakeryandroidapp.alignstech.model.Product;

import java.util.List;

/**
 * Created by Dell on 12/28/2015.
 */
public class AllItemsResultEvent {

    List<Product> dataList;

    public AllItemsResultEvent(List<Product> dataList){
        this.dataList = dataList;
    }

    public List<Product> getAllItemList(){
        return  dataList;
    }
}
