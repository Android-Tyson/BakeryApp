package com.urbangirlbakeryandroidapp.alignstech.fragment_dialog;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Welcome_Screen extends DialogFragment implements View.OnClickListener {

//    @InjectView(R.id.user_circular_imageView)
//    CircularImageView user_pic;

    @InjectView(R.id.user_name)
    TextView user_name;

    @InjectView(R.id.user_phone)
    TextView user_phone;

    @InjectView(R.id.user_mail)
    TextView user_mail;

    @InjectView(R.id.user_go)
    TextView user_go;

    public static Welcome_Screen newInstance() {

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
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (DataBase_Utils.isUserInfoDataExists()) {

            List<DataBase_UserInfo> userDetials = DataBase_Utils.getUserInfoList();
            String userName = userDetials.get(0).getFirstName() + " " + userDetials.get(0).getLastName();
            String userPhone = userDetials.get(0).getMobilePrimary();
            String userEmail = userDetials.get(0).getEmail();


            user_name.setText(userName);
            user_mail.setText(userEmail);
            user_phone.setText(userPhone);

        }
        user_go.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
