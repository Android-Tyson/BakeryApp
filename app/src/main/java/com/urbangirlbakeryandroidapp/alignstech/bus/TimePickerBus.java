package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/5/2016.
 */
public class TimePickerBus {

    String currentTime;

    public TimePickerBus(String currentTime){
        this.currentTime = currentTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
