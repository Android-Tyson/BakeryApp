package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Dell on 12/22/2015.
 */
public class MyUtils {

    public static String DIRECTORY_NAME = ".ugCake";
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

    public static void saveProfilePicture(Bitmap bitmap) {
        try {
            String dir = Environment.getExternalStorageDirectory().toString();
            File file = new File(dir, DIRECTORY_NAME);
            if (!file.exists()) {
                file.mkdirs();
            }

            File outputFile = new File(dir + File.separator + DIRECTORY_NAME + File.separator + PICTURE_FILE_NAME);
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(outputFile);
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getProfilePicture() {
        Bitmap bitmap;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + File.separator + DIRECTORY_NAME + File.separator + PICTURE_FILE_NAME, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showToast(Context context , String message){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public static void showLog(String message){
        Log.i("APP_TAG", message);
    }

}
