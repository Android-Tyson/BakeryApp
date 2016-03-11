package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.DatePickerBus;

import java.util.Arrays;
import java.util.List;

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

        String holiday = MyUtils.getDataFromPreferences(context , "HOLIDAY");
        String[] holiday_list = holiday.split(",");
        List<String> wordList = Arrays.asList(holiday_list);

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

        String currentDate = i + "-"+ month + "-" + day;

        if(wordList.contains(currentDate)){
            showDialog(context , currentDate);
        }else {
            MyBus.getInstance().post(new DatePickerBus(currentDate));
        }
    }

    private void showDialog(Context context , String date){

        new MaterialDialog.Builder(context)
                .title("Notice")
                .content("There will be holiday on this date ("+ date +"). So, please select another date. ")
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
