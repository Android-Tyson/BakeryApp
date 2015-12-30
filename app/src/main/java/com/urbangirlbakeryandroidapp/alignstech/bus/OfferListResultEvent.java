package com.urbangirlbakeryandroidapp.alignstech.bus;

import com.urbangirlbakeryandroidapp.alignstech.model.Offers;

import java.util.List;

/**
 * Created by Dell on 12/28/2015.
 */
public class OfferListResultEvent {

    List<Offers> dataList;

    public OfferListResultEvent(List<Offers> dataList){
        this.dataList = dataList;
    }

    public List<Offers> getOfferList(){
        return  dataList;
    }
}
