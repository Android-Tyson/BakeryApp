package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dell on 12/22/2015.
 */
public class MyUtils {

    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        } else {
            return networkInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                    networkInfo.getType() == ConnectivityManager.TYPE_MOBILE ||
                    networkInfo.getType() == ConnectivityManager.TYPE_WIMAX;
        }
    }

    public static void saveDataInPreferences(Context context , String key , String value){

        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key , value);
        editor.commit();

    }

    public static String checkDataFromPreferences(Context context , String key){

        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS" , Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(key  , "");

        return result;
    }

}
