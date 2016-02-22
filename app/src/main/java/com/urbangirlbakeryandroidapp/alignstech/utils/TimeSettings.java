package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import com.urbangirlbakeryandroidapp.alignstech.bus.TimePickerBus;

/**
 * Created by Dell on 2/22/2016.
 */
public class TimeSettings  implements TimePickerDialog.OnTimeSetListener{

    Context context;

    public TimeSettings(Context context){
        this.context = context;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String am_pm ;
        if(i < 12) {
            am_pm = "AM";
        } else {
            am_pm = "PM";
        }
        String selectedTime = i+" : "+ i1+" : "+ am_pm;
        MyBus.getInstance().post(new TimePickerBus(selectedTime));
    }

}
