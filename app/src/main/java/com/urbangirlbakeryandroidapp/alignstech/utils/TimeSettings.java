package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.TimePickerBus;

/**
 * Created by Dell on 2/22/2016.
 */

public class TimeSettings implements TimePickerDialog.OnTimeSetListener {


    private Context context;


    public TimeSettings(Context context) {
        this.context = context;
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        int openingHour = openingHour(MyUtils.getDataFromPreferences(context, "OPENING_HOUR"));
        int closingHour = closingHour(MyUtils.getDataFromPreferences(context, "CLOSING_HOUR"));

        if(i >= openingHour && i1 < closingHour){

            MyUtils.showToast(context , "Good to go with...");

        }else {

            showDialog(context);

        }

        String am_pm, hour, minute;

        if (i < 12) {
            am_pm = "AM";
        } else {
            am_pm = "PM";
        }

        if (i > 12)
            i = i - 12;

        if (i < 10) {
            hour = "0" + i;
        } else {
            hour = String.valueOf(i);
        }

        if (i1 < 10) {
            minute = "0" + i1;
        } else {
            minute = String.valueOf(i1);
        }

        String selectedTime = hour + " : " + minute + " : " + am_pm;
        MyBus.getInstance().post(new TimePickerBus(selectedTime));
    }


    private int openingHour(String string){
        String[] list = string.split(":");
        int time = Integer.parseInt(list[0]);

        return time;
    }


    private int closingHour(String string){

        String[] list = string.split(":");
        int time = Integer.parseInt(list[0]);

        return time;
    }


    private void showDialog(Context context){

        new MaterialDialog.Builder(context)
                .title("Notice")
                .content("You must place your order before "+
                        MyUtils.getDataFromPreferences(context, "OPENING_HOUR") +
                        " and after " + MyUtils.getDataFromPreferences(context, "CLOSING_HOUR"))
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
