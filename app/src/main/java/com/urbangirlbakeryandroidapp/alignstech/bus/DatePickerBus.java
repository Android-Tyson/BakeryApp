package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/5/2016.
 */
public class DatePickerBus {

    String currentDate;

    public DatePickerBus(String currentDate){
        this.currentDate = currentDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }
}
