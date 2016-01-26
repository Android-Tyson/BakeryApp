package com.urbangirlbakeryandroidapp.alignstech.bus;

/**
 * Created by Dell on 1/10/2016.
 */
public class CheckBoxEventBus {

    boolean errorResponse;

    public CheckBoxEventBus(boolean errorResponse) {
        this.errorResponse = errorResponse;
    }

    public boolean isErrorResponse() {
        return errorResponse;
    }

}
