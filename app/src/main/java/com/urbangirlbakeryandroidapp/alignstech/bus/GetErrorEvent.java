package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/5/2016.
 */
public class GetErrorEvent {

    String error;

    public GetErrorEvent(String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
