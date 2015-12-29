package com.urbangirlbakeryandroidapp.alignstech.bus;

import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;

import java.util.List;

/**
 * Created by Dell on 12/28/2015.
 */
public class NavListResultEvent {

    List<Cakes> dataList;

    public NavListResultEvent(List<Cakes> dataList){
        this.dataList = dataList;
    }

    public List<Cakes> getCakeList(){
        return  dataList;
    }
}
