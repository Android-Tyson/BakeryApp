package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.urbangirlbakeryandroidapp.alignstech.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_Screen extends DialogFragment {

    public static Welcome_Screen newInstance(){

        Welcome_Screen welcome_screen = new Welcome_Screen();
        return welcome_screen;

    }

    public Welcome_Screen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_welcome__screen, container, false);
    }


}
