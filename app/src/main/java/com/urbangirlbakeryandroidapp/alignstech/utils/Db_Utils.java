package com.urbangirlbakeryandroidapp.alignstech.utils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.urbangirlbakeryandroidapp.alignstech.model.Accessories;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.model.Gifts;
import com.urbangirlbakeryandroidapp.alignstech.model.Offers;

import java.util.List;

/**
 * Created by Dell on 12/23/2015.
 */
public class Db_Utils {

    public static boolean isUserInfoDataExists() {

        List<DataBase_UserInfo> queryResults = new Select().from(DataBase_UserInfo.class).execute();
        boolean check = queryResults.isEmpty();
        if (check) {
            return false;
        } else {
            return true;
        }

    }

    public static List<DataBase_UserInfo> getUserInfoList() {

        return new Select().from(DataBase_UserInfo.class).execute();

    }

    public static void deleteUserInfoList() {

         new Delete().from(DataBase_UserInfo.class).execute();

    }



    public static boolean isCakeListDataExists() {

        List<Cakes> queryResults = new Select().from(Cakes.class).execute();
        boolean check = queryResults.isEmpty();
        if (check) {
            return false;
        } else {
            return true;
        }

    }

    public static List<Cakes> getCakesList() {

        return new Select().from(Cakes.class).execute();

    }

    public static void deleteOldCakeListData() {

        if (Db_Utils.getCakesList().size() > 0) {
            new Delete().from(Cakes.class).execute();
        }

    }



    public static boolean isAccessoriesListDataExists() {

        List<Accessories> queryResults = new Select().from(Accessories.class).execute();
        boolean check = queryResults.isEmpty();
        if (check) {
            return false;
        } else {
            return true;
        }

    }

    public static List<Accessories> getAccessoriesList() {

        return new Select().from(Accessories.class).execute();

    }

    public static void deleteOldAccessoriesListData() {

        if (Db_Utils.getAccessoriesList().size() > 0) {
            new Delete().from(Accessories.class).execute();
        }

    }


    public static boolean isGiftListDataExists() {

        List<Gifts> queryResults = new Select().from(Gifts.class).execute();
        boolean check = queryResults.isEmpty();
        if (check) {
            return false;
        } else {
            return true;
        }

    }

    public static List<Gifts> getGiftList() {

        return new Select().from(Gifts.class).execute();

    }

    public static void deleteOldGiftListData() {

        if (Db_Utils.getGiftList().size() > 0) {
            new Delete().from(Gifts.class).execute();
        }

    }



    public static boolean isOfferListDataExists() {

        List<Offers> queryResults = new Select().from(Offers.class).execute();
        boolean check = queryResults.isEmpty();
        if (check) {
            return false;
        } else {
            return true;
        }

    }

    public static List<Offers> getOfferList() {

        return new Select().from(Offers.class).execute();

    }

    public static void deleteOldOfferListData() {

        if (Db_Utils.getOfferList().size() > 0 ) {
            new Delete().from(Offers.class).execute();
        }

    }

}
