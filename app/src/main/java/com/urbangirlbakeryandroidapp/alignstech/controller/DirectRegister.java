package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 1/5/2016.
 */
public class DirectRegister {

    public static ProgressDialog progressDialog;

    public static void postUserDetials(String url, Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Posting Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyUtils.showLog(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showLog(error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("fb_id", "134535345341");
                params.put("mobile_no", "9999999999");
                params.put("email", "asa@gmail.com");
                params.put("dob", "11-11-1111");
                params.put("gender", "male");
                params.put("zone", "myZone");
                params.put("district", "myDistrict");
                params.put("location", "myLocation");
                params.put("full_name", "myFullName");


                return params;
            }
        };
        jsonStringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonStringRequest);

    }

}
