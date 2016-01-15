package com.urbangirlbakeryandroidapp.alignstech.bus;

import org.json.JSONObject;

/**
 * Created by Dell on 1/13/2016.
 */
public class SeeAllCategoriesEvent {

    JSONObject jsonObject;

    public SeeAllCategoriesEvent(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

}
