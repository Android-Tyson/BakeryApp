package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.SeeAllCategoriesEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;

import org.json.JSONObject;

/**
 * Created by Dell on 1/13/2016.
 */
public class GetAllCategoriesCake {

    private static MaterialDialog materialDialog;
    public static void parseAllCategoriesList(String url , final Context context){

        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true , 0).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        MyBus.getInstance().post(new SeeAllCategoriesEvent(response));
                        materialDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                MyUtils.showToast(context, error.toString());
                materialDialog.dismiss();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjectRequest , "GET_ALL_CATEGORIES_TAG");

    }

}
