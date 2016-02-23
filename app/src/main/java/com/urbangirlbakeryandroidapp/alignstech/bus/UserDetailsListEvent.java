package com.urbangirlbakeryandroidapp.alignstech.bus;

import java.util.ArrayList;

/**
 * Created by Dell on 2/23/2016.
 */
public class UserDetailsListEvent {

    ArrayList<String> userDetailsList;

    public UserDetailsListEvent(ArrayList<String> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }

    public ArrayList<String> getUserDetailsList() {
        return userDetailsList;
    }


}
