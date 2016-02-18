package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostOrderUser;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ordered_Details extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    @InjectView(R.id.deliveryAddress)
    EditText deliveryAddress;

    @InjectView(R.id.contactPersonName)
    EditText contactPersonName;

    @InjectView(R.id.phone_1)
    EditText phone_1;

    @InjectView(R.id.phone_2)
    EditText phone_2;

//    @InjectView(R.id.dateTime)
//    EditText dateTime;

    @InjectView(R.id.short_message)
    EditText shortMessage;

    @InjectView(R.id.order)
    Button order;

    private ArrayList<String> userPostDetails = new ArrayList<>();

    public Ordered_Details() {
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
        View view = inflater.inflate(R.layout.fragment_order__details, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        order.setOnClickListener(this);
    }

    private boolean ifAnyFieldsAreNotEmpty() {

        String del_address = deliveryAddress.getText().toString();
        String contact_Name = contactPersonName.getText().toString();
        String phone1 = phone_1.getText().toString();
        String phone2 = phone_2.getText().toString();
//        String date_time = dateTime.getText().toString();
        String short_message = shortMessage.getText().toString();

        if (!del_address.isEmpty()
                && !contact_Name.isEmpty()
                && !phone1.isEmpty()
                && !phone2.isEmpty()
//                && !date_time.isEmpty()
                && !short_message.isEmpty()
                ) {

            if (MyUtils.isValidPhoneNumber(phone1, getActivity())
                    && MyUtils.isValidPhoneNumber(phone2, getActivity())) {
                userPostDetails.add(del_address);
                userPostDetails.add(contact_Name);
                userPostDetails.add(phone1);
                userPostDetails.add(phone2);
                userPostDetails.add(MyUtils.getCurrentDate());
                userPostDetails.add(short_message);

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    @Override
    public void onClick(View view) {

        if (ifAnyFieldsAreNotEmpty()) {
            PostOrderUser.postOrderUserDetails(Apis.order_user_details, getActivity(), userPostDetails);
            getDialog().dismiss();
        } else {
            MyUtils.showToast(getActivity(), "Please Check all the Details.");
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }
}
