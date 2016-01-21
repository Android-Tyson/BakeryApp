package com.urbangirlbakeryandroidapp.alignstech.bus;

import org.json.JSONObject;

/**
 * Created by Dell on 12/28/2015.
 */
public class AccessoriesListResultEvent {

    JSONObject jsonObject;

    public AccessoriesListResultEvent(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

}
