package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import com.urbangirlbakeryandroidapp.alignstech.bus.TimePickerBus;

/**
 * Created by Dell on 2/22/2016.
 */
public class TimeSettings implements TimePickerDialog.OnTimeSetListener {

    Context context;

    public TimeSettings(Context context) {
        this.context = context;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
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

}
