package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/5/2016.
 */
public class NormalRegisterEventBus {

    String response;

    public NormalRegisterEventBus(String response){
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
