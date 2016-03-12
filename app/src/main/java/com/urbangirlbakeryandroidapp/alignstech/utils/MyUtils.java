package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProfilePicture;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dell on 12/22/2015.
 */
public class MyUtils
{

    public static boolean isValidPhoneNumber(String phoneNo , Context context) {
        CharSequence target = phoneNo;
        if (target == null) {
            return false;
        } else {
            if (target.length() < 8 || target.length() > 13) {
                MyUtils.showToast(context , "Please Enter a Valid Phone Number...");
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }


    public static boolean isEmailValid(String email , Context context) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        } else {
            MyUtils.showToast(context, "Please enter a valid mail..");
            return false;
        }
    }


    public static void setUserProfilePicture(Context context) {

        List<DataBase_UserInfo> queryResults = DataBase_Utils.getUserInfoList();
        if (queryResults.size() > 0) {
            GetProfilePicture.userProfilePicture(context, queryResults.get(0).getProfilePicUrl());
        }
    }


    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            showToast(context , "Please Check your internet connection and try again..");
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


    public static String getDataFromPreferences(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(key, "");
        return result;
    }

    public static void editDataOfPreferences(Context context, String key , String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void removeSingleSharedPreference(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
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


    public static String getCurrentDate() {

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());

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


    public static void logOut(final Activity context)
    {
        SimpleFacebook.getInstance(context).logout(new OnLogoutListener() {
            @Override
            public void onLogout() {
                MyUtils.showLog("");
                SharedPreferences userInfo = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
                userInfo.edit().remove("USER_LOGGED_IN").apply();
                userInfo.edit().remove("USER_ID").apply();
                DataBase_Utils.deleteUserInfoList();
                Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
                MyUtils.showLog("  ");
                MyUtils.showLog("  ");
            }
        });
    }

    public static void restartApplication(Context context)
    {
        Intent mStartActivity = new Intent(context, MainActivity.class);
        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    private void dialogIfNotLoggedIn(final Context context) {

        new MaterialDialog.Builder(context)
                .title("Please Login")
                .content("You Are not Logged In. Please login to continue..")
                .positiveText("Login")
                .negativeText("Later")
                .autoDismiss(true)
                .positiveColorRes(R.color.myPrimaryColor)
                .negativeColorRes(R.color.myPrimaryColor)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("LearningPattern", "true");
                        context.startActivity(intent);
//                        finish();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .build()
                .show();

    }
}
