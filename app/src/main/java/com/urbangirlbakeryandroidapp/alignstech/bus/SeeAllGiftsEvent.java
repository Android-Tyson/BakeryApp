package com.urbangirlbakeryandroidapp.alignstech.bus;

import org.json.JSONObject;

/**
 * Created by Dell on 1/13/2016.
 */
public class SeeAllGiftsEvent {

    JSONObject jsonObject;

    public SeeAllGiftsEvent(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

}
