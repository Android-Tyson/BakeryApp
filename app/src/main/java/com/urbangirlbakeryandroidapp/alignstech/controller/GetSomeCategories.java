package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.SomeCategoriesEventBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONObject;

/**
 * Created by Dell on 1/13/2016.
 */
public class GetSomeCategories {

    private static MaterialDialog materialDialog;
    public static void parseSomeCategoriesList(String url , final Context context){

        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true , 0).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MyBus.getInstance().post(new SomeCategoriesEventBus(response));
                        materialDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(context, error.toString());
                GetSomeCategories.parseSomeCategoriesList(Apis.some_categories_list , context);
                materialDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest , "HOME_SCREEN_RESPONSE");

    }

}
