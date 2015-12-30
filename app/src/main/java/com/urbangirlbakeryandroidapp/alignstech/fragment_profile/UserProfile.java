package com.urbangirlbakeryandroidapp.alignstech.fragment_profile;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends Fragment {

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
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }


}
