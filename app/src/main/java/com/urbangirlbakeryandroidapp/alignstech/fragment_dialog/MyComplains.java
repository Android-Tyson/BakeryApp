package com.urbangirlbakeryandroidapp.alignstech.fragment_dialog;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostUserCompains;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyComplains extends DialogFragment implements View.OnClickListener {

    @InjectView(R.id.close)
    TextView close;

    @InjectView(R.id.postText)
    EditText postText;

    @InjectView(R.id.post)
    TextView post;

    private ArrayList<String> userInfoList = new ArrayList<>();
    private String user_address;

    public MyComplains() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_my_complains, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        close.setOnClickListener(this);
        post.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.close) {
            getDialog().dismiss();
        } else if (view.getId() == R.id.post) {

            getUserInfoList();
            getDialog().dismiss();
            if (!postText.getText().toString().isEmpty()) {
                if (user_address != null) {

                    PostUserCompains.postUserComplain(Apis.post_complain, getActivity(), userInfoList);

                } else {
                    MyUtils.showToast(getActivity(), "Please Fill all the additional Information to your profile");
                }
            } else {
                MyUtils.showToast(getActivity(), "Please Fill all the fields");
            }

        }

    }

    private void getUserInfoList() {

        List<DataBase_UserInfo> userDetials = DataBase_Utils.getUserInfoList();
        String user_id = userDetials.get(0).getFb_id() + "";
        user_address = userDetials.get(0).getLocation();
        String message = postText.getText().toString();

        userInfoList.add(user_id);
        userInfoList.add(message);
        userInfoList.add(user_address);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }
}
