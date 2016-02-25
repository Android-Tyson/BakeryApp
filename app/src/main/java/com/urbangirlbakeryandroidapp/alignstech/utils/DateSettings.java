package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.urbangirlbakeryandroidapp.alignstech.bus.DatePickerBus;

/**
 * Created by Dell on 2/22/2016.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener{

    Context context;

    public DateSettings(Context context){
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        i1 += 1;
        String month , day;

        if(i1 < 10){
            month = "0" + i1;
        }else {
            month = String.valueOf(i1);
        }

        if(i2 < 10){
            day = "0" + i2;
        }else {
            day = String.valueOf(i2);
        }

        String currentDate = day + "/"+ month + "/" + i;
        MyBus.getInstance().post(new DatePickerBus(currentDate));
    }
}
