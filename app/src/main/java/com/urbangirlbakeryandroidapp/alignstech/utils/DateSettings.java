package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.activity.SingleItemDetails;
import com.urbangirlbakeryandroidapp.alignstech.bus.DatePickerBus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dell on 2/22/2016.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener{

    Context context;
    private int currentYear = 0 ;

    public DateSettings(Context context){
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1 += 1;
        currentYear = i ;

        i2 = i2 + Integer.parseInt(SingleItemDetails.delivery_date);
        int additionalDay = dateValidation(i1 , i2);
        if( additionalDay > 0){
            i1 += 1;
            i2 = additionalDay;
        }

        String holiday = MyUtils.getDataFromPreferences(context , "HOLIDAY");
        String[] holiday_list = holiday.split(",");
        List<String> wordList = Arrays.asList(holiday_list);

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
            holidayDialog(context, currentDate);
        }else {
            additionalDayDialog(currentDate);
            MyBus.getInstance().post(new DatePickerBus(currentDate));
        }
    }

    private void holidayDialog(Context context, String date){

        new MaterialDialog.Builder(context)
                .title("Notice")
                .content("Sorry, There will be holiday on this date ("+ date +"). So, please select another date. ")
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

    private void additionalDayDialog(String date){

        if(Integer.parseInt(SingleItemDetails.delivery_date) > 0){

            new MaterialDialog.Builder(context)
                    .title("Notice")
                    .content("The time to make this cake is" + SingleItemDetails.delivery_date +
                            " days. The cake will be delivered by adding the " +
                            "additional time to makeup that cake." +
                            " So the cake delivery date will be on "+ date + ".")
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


        }else if(Integer.parseInt(SingleItemDetails.delivery_date) == 0){

            new MaterialDialog.Builder(context)
                    .title("Notice")
                    .content("This is the urgent cake and can be delivered today.")
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


    private int dateValidation(int month , int day){

        int additionalDay = 0;
//        day = day + Integer.parseInt(SingleItemDetails.delivery_date);
        if(month == 1){
            if(day > 31){
                additionalDay = day - 31;
            }
        }else if(month == 2){

            if(currentYear % 400 == 0 || (currentYear % 4 == 0) && (currentYear % 100 != 0)){
                MyUtils.showLog("This is leap year");
                if(day > 29){
                    additionalDay = day - 29;
                }
            }else {
                MyUtils.showLog("This is not leap year");
                if(day > 28){
                    additionalDay = day - 28;
                }
            }

        }else if(month == 3){
            if(day > 31){
                additionalDay = day - 31;
            }
        }else if(month == 4){
            if(day > 30){
                additionalDay = day - 30;
            }
        }else if(month == 5){
            if(day > 31){
                additionalDay = day - 31;
            }
        }else if(month == 6){
            if(day > 30){
                additionalDay = day - 30;
            }
        }else if(month == 7){
            if(day > 31){
                additionalDay = day - 31;
            }
        }else if(month == 8){
            if(day > 31){
                additionalDay = day - 31;
            }
        }else if(month == 9){
            if(day > 30){
                additionalDay = day - 30;
            }
        }else if(month == 10){
            if(day > 31){
                additionalDay = day - 31;
            }
        }else if(month == 11){
            if(day > 30){
                additionalDay = day - 30;
            }
        }else if(month == 12){
            if(day > 31){
                additionalDay = day - 31;
            }
        }

        return additionalDay;
    }
}
