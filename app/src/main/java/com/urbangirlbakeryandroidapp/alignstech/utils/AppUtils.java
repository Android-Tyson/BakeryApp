package com.urbangirlbakeryandroidapp.alignstech.utils;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.urbangirlbakeryandroidapp.alignstech.model.Accessories;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.model.Gifts;
import com.urbangirlbakeryandroidapp.alignstech.model.Offers;

/**
 * Created by Dell on 11/23/2015.
 */
public class AppUtils extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Configuration.Builder configurationBuilder = new Configuration.Builder(this).setDatabaseName("my_database.db");
        configurationBuilder.addModelClass(DataBase_UserInfo.class);
        configurationBuilder.addModelClass(Cakes.class);
        configurationBuilder.addModelClass(Gifts.class);
        configurationBuilder.addModelClass(Offers.class);
        configurationBuilder.addModelClass(Accessories.class);
        ActiveAndroid.initialize(configurationBuilder.create());

        MyUtils.showLog("Active android initialize");
    }

}
