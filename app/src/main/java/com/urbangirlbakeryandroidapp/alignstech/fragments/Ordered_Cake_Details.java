package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.DatePickerBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.TimePickerBus;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostOrderCakeDetails;
import com.urbangirlbakeryandroidapp.alignstech.fragment_dialog.DateDialogHandler;
import com.urbangirlbakeryandroidapp.alignstech.fragment_dialog.TimeDialogHandler;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ordered_Cake_Details extends android.support.v4.app.Fragment implements View.OnClickListener  {

    private Context context;

    @InjectView(R.id.full_name)
    EditText contactPersonName;

    @InjectView(R.id.phone1)
    EditText phone_1;

    @InjectView(R.id.phone2)
    EditText phone_2;

    @InjectView(R.id.sippingAddress)
    EditText deliveryAddress;

    @InjectView(R.id.shortMessage)
    EditText shortMessage;

    @InjectView(R.id.order)
    Button order;

    @InjectView(R.id.datePicker)
    TextView tvDatePicker;

    @InjectView(R.id.timePicker)
    TextView tvTimePicker;

    private int hour , minute ;
    private String amPm;

    private ArrayList<String> userPostDetails = new ArrayList<>();

    public Ordered_Cake_Details() {
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
        View view = inflater.inflate(R.layout.fragment_order_cake_details, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        order.setOnClickListener(this);
        tvDatePicker.setOnClickListener(this);
        tvTimePicker.setOnClickListener(this);
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

        if(view.getId() == R.id.order) {
            if (ifAnyFieldsAreNotEmpty()) {

                android.support.v4.app.Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("FRAME_CONTAINER");
                if (fragment != null)
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                PostOrderCakeDetails.postOrderUserDetails(Apis.gift_order_details, getActivity(), userPostDetails);

            } else {
                MyUtils.showToast(getActivity(), "Please Check all the Details.");
            }
        }else if(view.getId() == R.id.datePicker){

            DateDialogHandler handler = new DateDialogHandler();
            handler.show(getFragmentManager() , "DATE_DIALOG");

        }else if(view.getId() == R.id.timePicker){

            TimeDialogHandler handler = new TimeDialogHandler();
            handler.show(getFragmentManager() , "TIMER_DIALOG");

        }
    }


    @Subscribe
    public void getSelectedDate(TimePickerBus event){
        tvTimePicker.setText(event.getCurrentTime());
    }

    @Subscribe
    public void getSelectedTime(DatePickerBus event){
        tvDatePicker.setText(event.getCurrentDate());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }

}
