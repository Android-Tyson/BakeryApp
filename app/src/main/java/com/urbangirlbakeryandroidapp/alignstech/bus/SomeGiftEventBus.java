package com.urbangirlbakeryandroidapp.alignstech.bus;

import org.json.JSONObject;

/**
 * Created by Dell on 1/13/2016.
 */
public class SomeGiftEventBus {

    JSONObject jsonObject;

    public SomeGiftEventBus(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

}