package com.urbangirlbakeryandroidapp.alignstech.utils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.model.Gifts;

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

}
