package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

/**
 * Created by Dell on 12/24/2015.
 */
public class GetProfilePicture {

    public static void userProfilePicture(final Context context, String url) {

        if (MyUtils.isNetworkConnected(context)) {
            if (DataBase_Utils.isUserInfoDataExists()) {
                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmapResponse) {

                        MainActivity.account.setPhoto(bitmapResponse);
                        MyUtils.saveUserProfiePic(bitmapResponse);

                    }
                }, 50, 50, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        MyUtils.showToast(context, error.toString());
                        List<DataBase_UserInfo> queryResults = DataBase_Utils.getUserInfoList();
                        GetProfilePicture.userProfilePicture(context, queryResults.get(0).getProfilePicUrl());

                    }
                });
                imageRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(imageRequest);
            }
        }
    }
}
