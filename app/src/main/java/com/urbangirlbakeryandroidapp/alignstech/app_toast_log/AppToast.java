package com.urbangirlbakeryandroidapp.alignstech.app_toast_log;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dell on 12/21/2015.
 */
public class AppToast {

    public static void showToast(Context context , String message){

        Toast.makeText(context , message , Toast.LENGTH_SHORT).show();

    }
}
