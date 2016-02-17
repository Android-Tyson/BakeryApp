package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/5/2016.
 */
public class OrderedUserDetailsEvent {

    String response;

    public OrderedUserDetailsEvent(String response){
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
