package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;

import java.util.List;

/**
 * Created by Dell on 12/22/2015.
 */
public class MyUtils {

    public static String DIRECTORY_NAME = ".BakeryApp";
    public static String PICTURE_FILE_NAME = "profile_picture.png";

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
        String result = sharedPreferences.getString(key, "");

        return result;
    }

    public static Bitmap getProfilePicture(Context context) {

        Bitmap bitmap;
        if(Db_Utils.isTableDataExists()){
            List<DataBase_UserInfo> queryResults = new Select().from(DataBase_UserInfo.class).execute();
            final Bitmap finalBitmap = bitmap;
            ImageRequest imageRequest = new ImageRequest(queryResults.get(0).getProfilePicUrl(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    finalBitmap = response;
                }
            }, 50, 50, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            MySingleton.getInstance(context).addToRequestQueue(imageRequest);
        }
        return bitmap[0];
    }
}
