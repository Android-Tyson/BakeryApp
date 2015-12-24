package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;
import android.graphics.Bitmap;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.urbangirlbakeryandroidapp.alignstech.HomeActivity;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

/**
 * Created by Dell on 12/24/2015.
 */
public class GetProfilePicture {

    public static void getProfilePicture(final Context context , String url) {

        if (MyUtils.isNetworkConnected(context)) {
            if (Db_Utils.isTableDataExists()) {
                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmapResponse) {
                        HomeActivity.account.setPhoto(bitmapResponse);
                    }
                }, 50, 50, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyUtils.showLog(error.toString());
                        List<DataBase_UserInfo> queryResults = new Select().from(DataBase_UserInfo.class).execute();
                        GetProfilePicture.getProfilePicture(context, queryResults.get(0).getProfilePicUrl());
                    }
                });
                MySingleton.getInstance(context).addToRequestQueue(imageRequest);
            }
        }
    }
}
