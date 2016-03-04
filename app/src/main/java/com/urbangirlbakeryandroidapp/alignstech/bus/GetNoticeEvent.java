package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/5/2016.
 */
public class GetNoticeEvent {

    String response;

    public GetNoticeEvent(String response){
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
