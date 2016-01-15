package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.SeeAllGiftsEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;

import org.json.JSONObject;

/**
 * Created by Dell on 1/13/2016.
 */
public class GetAllGifts {

    private static MaterialDialog materialDialog;
    public static void parseAllGiftList(String url , final Context context){

        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").progress(true , 0).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MyBus.getInstance().post(new SeeAllGiftsEvent(response));
                        materialDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GetAllGifts.parseAllGiftList(Apis.some_gift_list, context);
                materialDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest , "GET_ALL_GIFT_TAG");

    }

}
