package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/5/2016.
 */
public class PostComplainEvent {

    String response;

    public PostComplainEvent(String response){
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
