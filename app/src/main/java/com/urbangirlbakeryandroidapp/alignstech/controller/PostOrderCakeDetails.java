package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.OrderedUserDetailsEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 1/5/2016.
 */
public class PostOrderCakeDetails {

    private static MaterialDialog materialDialog;

    public static void postOrderUserDetails(String url, final Context context, final ArrayList<String> orderedUserDetail) {

        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true, 0).show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyBus.getInstance().post(new OrderedUserDetailsEvent(response));
                        materialDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(context, error.toString());
                materialDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("delivery_address", orderedUserDetail.get(0));
                params.put("contact_person_name", orderedUserDetail.get(1));
                params.put("phone_no1", orderedUserDetail.get(2));
                params.put("phone_no2", orderedUserDetail.get(3));
                params.put("date_time", orderedUserDetail.get(4));
                params.put("message_on_cake", orderedUserDetail.get(5));
                params.put("user_id", MyUtils.getDataFromPreferences(context, "USER_ID"));
                MyUtils.showLog(" ");

                return params;
            }
        };
        jsonStringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonStringRequest);

    }

}
