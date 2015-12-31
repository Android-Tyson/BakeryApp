package com.urbangirlbakeryandroidapp.alignstech.fragment_profile;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.urbangirlbakeryandroidapp.alignstech.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends android.support.v4.app.Fragment {

    public static ImageView userProfilePicture;

    public UserProfile() {
        // Required empty public constructor
    }

    public static UserProfile newInstance(){

        UserProfile userProfile = new UserProfile();
        return userProfile;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        userProfilePicture = (ImageView) view.findViewById(R.id.imageView_profile_picture);
        ButterKnife.inject(this , view);
        return view;
    }


}
