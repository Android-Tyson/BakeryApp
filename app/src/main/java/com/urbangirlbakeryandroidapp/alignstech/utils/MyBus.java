package com.urbangirlbakeryandroidapp.alignstech.utils;

import com.squareup.otto.Bus;

/**
 * Created by Dell on 12/28/2015.
 */
public class MyBus {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

}