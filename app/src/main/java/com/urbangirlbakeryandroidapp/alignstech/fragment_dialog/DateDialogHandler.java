package com.urbangirlbakeryandroidapp.alignstech.fragment_dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.urbangirlbakeryandroidapp.alignstech.utils.DateSettings;

import java.util.Calendar;

/**
 * Created by Dell on 2/22/2016.
 */
public class DateDialogHandler extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        DateSettings dateSettings = new DateSettings(getActivity());
        datePickerDialog = new DatePickerDialog(getActivity() , dateSettings , year , month , day);

        return datePickerDialog;

    }
}
