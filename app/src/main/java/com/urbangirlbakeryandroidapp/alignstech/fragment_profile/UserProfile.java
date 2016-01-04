package com.urbangirlbakeryandroidapp.alignstech.fragment_profile;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;
import com.urbangirlbakeryandroidapp.alignstech.view.CircularImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends android.support.v4.app.Fragment {

    @InjectView(R.id.imageView_profile_picture)
    public CircularImageView userProfilePicture;

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
        ButterKnife.inject(this, view);
        Bitmap bitmap = MyUtils.getUserProfilePic();
        MyUtils.showLog("");
        if(MyUtils.getUserProfilePic() != null) {
            userProfilePicture.setImageBitmap(MyUtils.getUserProfilePic());
        }else {
            MyUtils.showLog("");
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        UpdateFacebookUserDetials.updateUserDetials(Apis.user_list , getActivity());
    }
}
