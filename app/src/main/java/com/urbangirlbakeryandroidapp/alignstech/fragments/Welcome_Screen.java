package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_Screen extends DialogFragment {

    @InjectView(R.id.user_circular_imageView)
    NetworkImageView user_pic;

    @InjectView(R.id.user_phone)
    TextView user_phone;

    @InjectView(R.id.user_mail)
    TextView user_mail;

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
        View view = inflater.inflate(R.layout.fragment_welcome__screen, container, false);
        user_pic.setImageUrl("PROFILE_URL", MySingleton.getInstance(getActivity()).getImageLoader());
        return view;
    }


}
