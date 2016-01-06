package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.urbangirlbakeryandroidapp.alignstech.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_Screen extends DialogFragment {

    @InjectView(R.id.user_circular_imageView)
    CircularImageView user_pic;

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
        ButterKnife.inject(this, view);
//        user_pic.setImageUrl("http://sleepycabin.com/cabinshit/uploads/profile_builder/avatars/userID_2566_originalAvatar_profile_pic.png", MySingleton.getInstance(getActivity()).getImageLoader());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user_pic.setImageResource(R.drawable.drawer_bg);

    }
}
