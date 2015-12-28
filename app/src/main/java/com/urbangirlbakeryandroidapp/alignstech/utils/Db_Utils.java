package com.urbangirlbakeryandroidapp.alignstech.utils;

import com.activeandroid.query.Select;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;

import java.util.List;

/**
 * Created by Dell on 12/23/2015.
 */
public class Db_Utils {

    public static boolean isUserInfoDataExists(){

        List<DataBase_UserInfo> queryResults = new Select().from(DataBase_UserInfo.class).execute();
        boolean check = queryResults.isEmpty();
        if(check){
            return false;
        }else{
            return true;
        }

    }

    public static List<DataBase_UserInfo> getUserInfoList(){

        return new Select().from(DataBase_UserInfo.class).execute();

    }

    public static List<Cakes> getCakesList(){

        return new Select().from(Cakes.class).execute();

    }


}
