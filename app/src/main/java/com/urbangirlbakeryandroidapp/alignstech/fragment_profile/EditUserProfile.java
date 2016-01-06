package com.urbangirlbakeryandroidapp.alignstech.fragment_profile;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserProfile extends android.support.v4.app.Fragment {

    @InjectView(R.id.imageView_profile_picture)
    public NetworkImageView userProfilePicture;

    public EditUserProfile() {
        // Required empty public constructor
    }

    public static EditUserProfile newInstance(){

        EditUserProfile userProfile = new EditUserProfile();
        return userProfile;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.inject(this, view);
        if(!Db_Utils.getUserInfoList().isEmpty()) {
            List<DataBase_UserInfo> list = Db_Utils.getUserInfoList();
            String profilePicUrl = list.get(0).getProfilePicUrl();
            userProfilePicture.setImageUrl(profilePicUrl , MySingleton.getInstance(getActivity()).getImageLoader());
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
