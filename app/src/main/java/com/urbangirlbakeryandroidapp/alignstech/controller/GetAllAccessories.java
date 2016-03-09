package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.AccessoriesListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetErrorEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;

import org.json.JSONObject;

/**
 * Created by Dell on 1/13/2016.
 */
public class GetAllAccessories {

//    private static MaterialDialog materialDialog;
    public static void parseAllAccessoriesList(String url, final Context context){

//        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true , 0).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MyBus.getInstance().post(new AccessoriesListResultEvent(response));
//                        materialDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                MyUtils.showToast(context, error.toString());
                MyBus.getInstance().post(new GetErrorEvent(error.toString()));

//                materialDialog.dismiss();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjectRequest , "GET_ALL_ACCESSORIES_TAG");

    }

}
