package com.urbangirlbakeryandroidapp.alignstech.bus;

import com.urbangirlbakeryandroidapp.alignstech.model.Gifts;

import java.util.List;

/**
 * Created by Dell on 12/28/2015.
 */
public class GiftListResultEvent {

    List<Gifts> dataList;

    public GiftListResultEvent(List<Gifts> dataList){
        this.dataList = dataList;
    }

    public List<Gifts> getGiftList(){
        return  dataList;
    }
}
