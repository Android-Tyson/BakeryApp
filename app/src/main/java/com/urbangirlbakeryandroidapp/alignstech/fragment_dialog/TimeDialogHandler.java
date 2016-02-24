package com.urbangirlbakeryandroidapp.alignstech.fragment_dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.utils.TimeSettings;

import java.util.Calendar;

/**
 * Created by Dell on 2/22/2016.
 */
public class TimeDialogHandler extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        TimeSettings timeSettings = new TimeSettings(getActivity());
        timePickerDialog = new TimePickerDialog(getActivity() , R.style.DialogTheme  , timeSettings ,
                hour , minute , false);

        return timePickerDialog;
    }
}
