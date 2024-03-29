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

import com.urbangirlbakeryandroidapp.alignstech.controller.GetProfilePicture;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Dell on 12/22/2015.
 */
public class MyUtils
{

    public static void setUserProfilePicture(Context context) {

        List<DataBase_UserInfo> queryResults = Db_Utils.getUserInfoList();
        if (queryResults.size() > 0) {
            GetProfilePicture.userProfilePicture(context, queryResults.get(0).getProfilePicUrl());
        }
    }

    public static boolean isNetworkConnected(Context context)
    {
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


    public static boolean isUserLoggedIn(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        String check = sharedPreferences.getString("USER_LOGGED_IN", "");

        if(!check.isEmpty()){
            return true;
        }else{
            return false;
        }
    }


    public static void saveDataInPreferences(Context context, String key, String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String checkDataFromPreferences(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(key, "");
        return result;
    }


    public static void saveUserProfiePic(Bitmap finalBitmap)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
//        Random generator = new Random();
//        int randomName = 10000;
//        randomName = generator.nextInt(n);
        String fname = "profile.jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getUserProfilePic()
    {
        Bitmap bitmap;
        String root = Environment.getExternalStorageDirectory().toString() + "/saved_images/profile.jpg";
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 8;
            bitmap = BitmapFactory.decodeFile(root  , options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showToast(Context context, String message)
    {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }


    public static void showLog(String message)
    {
        Log.i("APP_TAG", message);
    }

}
