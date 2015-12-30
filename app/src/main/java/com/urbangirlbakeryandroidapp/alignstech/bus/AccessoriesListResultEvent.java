package com.urbangirlbakeryandroidapp.alignstech.bus;

import com.urbangirlbakeryandroidapp.alignstech.model.Accessories;

import java.util.List;

/**
 * Created by Dell on 12/28/2015.
 */
public class AccessoriesListResultEvent {

    List<Accessories> dataList;

    public AccessoriesListResultEvent(List<Accessories> dataList){
        this.dataList = dataList;
    }

    public List<Accessories> getAccessoriesList(){
        return  dataList;
    }
}
