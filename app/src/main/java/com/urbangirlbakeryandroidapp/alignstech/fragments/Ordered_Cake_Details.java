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

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.DatePickerBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.TimePickerBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.UserDetailsListEvent;
import com.urbangirlbakeryandroidapp.alignstech.fragment_dialog.DateDialogHandler;
import com.urbangirlbakeryandroidapp.alignstech.fragment_dialog.TimeDialogHandler;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
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
public class Ordered_Cake_Details extends android.support.v4.app.Fragment implements View.OnClickListener {

    @InjectView(R.id.full_name)
    EditText fullName;

    @InjectView(R.id.email)
    EditText email;

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

    @InjectView(R.id.sender_name)
    EditText sender_name;

    @InjectView(R.id.receiver_name)
    EditText receiver_name;

    private ArrayList<String> userPostDetails = new ArrayList<>();
    private boolean isOpenedOnce = true;

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
        fillDataToEditText();
        order.setOnClickListener(this);
        tvDatePicker.setOnClickListener(this);
        tvTimePicker.setOnClickListener(this);
        dialogForOpeningAndClosingTime(getActivity());
    }


    private void fillDataToEditText() {

        if (DataBase_Utils.isUserInfoDataExists()) {

            List<DataBase_UserInfo> infoList = DataBase_Utils.getUserInfoList();
            String user_fullName = infoList.get(0).getFirstName() + " " + infoList.get(0).getLastName();
            String user_email = infoList.get(0).getEmail();
            String primary_phone = infoList.get(0).getMobilePrimary();
            String secondary_phone = infoList.get(0).getMobileSecondary();
            String delivery_address = infoList.get(0).getSippingAddress();

            if (!user_fullName.isEmpty())
                fullName.setText(user_fullName);

            if (user_email != null && !user_email.isEmpty())
                email.setText(user_email);

            if (primary_phone != null && !primary_phone.isEmpty())
                phone_1.setText(primary_phone);

            if (secondary_phone != null && !secondary_phone.isEmpty())
                phone_2.setText(secondary_phone);

            if (delivery_address != null && !delivery_address.isEmpty())
                deliveryAddress.setText(delivery_address);
        }

    }


    private boolean ifAnyFieldsAreNotEmpty() {

        String full_name = fullName.getText().toString();
        String phone1 = phone_1.getText().toString();
        String phone2 = phone_2.getText().toString();
        String delivery_address = deliveryAddress.getText().toString();
        String message_on_cake = shortMessage.getText().toString();
        String datePicker = tvDatePicker.getText().toString();
        String timePicker = tvTimePicker.getText().toString();
        String email_addr = email.getText().toString();
        String senderName = sender_name.getText().toString();
        String receiverName = receiver_name.getText().toString();

        if (!delivery_address.isEmpty()
                && !full_name.isEmpty()
                && !phone1.isEmpty()
                && !phone2.isEmpty()
                && !message_on_cake.isEmpty()
                && !datePicker.isEmpty()
                && !timePicker.isEmpty()
                ) {

            if (!email_addr.isEmpty())
                email_addr = "Empty Mail";

            if (MyUtils.isValidPhoneNumber(phone1, getActivity())
                    && MyUtils.isValidPhoneNumber(phone2, getActivity())) {

                if(!datePicker.equals("Select Date") && !timePicker.equals("Select Time")) {

                    userPostDetails.add(full_name);
                    userPostDetails.add(phone1);
                    userPostDetails.add(phone2);
                    userPostDetails.add(delivery_address);
                    userPostDetails.add(message_on_cake);
                    userPostDetails.add(datePicker + " " + timePicker);
                    userPostDetails.add(email_addr);
                    userPostDetails.add(senderName);
                    userPostDetails.add(receiverName);

                    return true;
                }else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.order) {
            if (ifAnyFieldsAreNotEmpty()) {

                android.support.v4.app.Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("FRAME_CONTAINER");
                if (fragment != null) {
                    MyBus.getInstance().post(new UserDetailsListEvent(userPostDetails));
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }

            } else {
                MyUtils.showToast(getActivity(), "Please Fill all the Details.");
            }
        } else if (view.getId() == R.id.datePicker) {

            DateDialogHandler handler = new DateDialogHandler();
            handler.show(getFragmentManager(), "DATE_DIALOG");

        } else if (view.getId() == R.id.timePicker) {

            TimeDialogHandler handler = new TimeDialogHandler();
            handler.show(getFragmentManager(), "TIMER_DIALOG");

        }
    }


    @Subscribe
    public void getSelectedDate(TimePickerBus event) {
        tvTimePicker.setText(event.getCurrentTime());
    }

    @Subscribe
    public void getSelectedTime(DatePickerBus event) {
        tvDatePicker.setText(event.getCurrentDate());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }

    private void dialogForOpeningAndClosingTime(final Context context) {

        new MaterialDialog.Builder(context)
                .title("Notice")
                .content("Please order this cake before: " + MyUtils.getDataFromPreferences(context, "OPENING_HOUR")
                        + " and after: " + MyUtils.getDataFromPreferences(context, "CLOSING_HOUR"))
                .positiveText("Ok")
                .cancelable(false)
                .positiveColorRes(R.color.myPrimaryColor)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                    }
                })
                .build()
                .show();

    }
}
