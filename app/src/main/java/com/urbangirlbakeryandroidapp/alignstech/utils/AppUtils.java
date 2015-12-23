package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;

/**
 * Created by Dell on 11/23/2015.
 */
public class AppUtils extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Configuration.Builder configurationBuilder = new Configuration.Builder(this).setDatabaseName("my_database.db");
        configurationBuilder.addModelClass(DataBase_UserInfo.class);
        ActiveAndroid.initialize(configurationBuilder.create());

        AppLog.showLog("Active android initialize");
    }

}
