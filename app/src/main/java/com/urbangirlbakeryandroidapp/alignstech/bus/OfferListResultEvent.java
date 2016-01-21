package com.urbangirlbakeryandroidapp.alignstech.bus;

import org.json.JSONObject;

/**
 * Created by Dell on 12/28/2015.
 */
public class OfferListResultEvent {

    JSONObject jsonObject;

    public OfferListResultEvent(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }


}
