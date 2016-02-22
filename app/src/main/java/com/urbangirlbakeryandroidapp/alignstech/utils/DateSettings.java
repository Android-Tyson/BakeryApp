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
        String currentDate = i + "-"+ i1 + "-" + i2;
        MyBus.getInstance().post(new DatePickerBus(currentDate));
    }
}
