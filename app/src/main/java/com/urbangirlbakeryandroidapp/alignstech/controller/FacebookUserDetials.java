package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppLog;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 12/22/2015.
 */
public class FacebookUserDetials {

    private static ProgressDialog progressDialog ;

    public static void postUserDetials(String url , Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Posting Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppLog.showLog(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.showLog(error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("fb_id", "3232j2j3j232");
                params.put("mobile_no", "8989989898");
                params.put("email", "abc@gmail.com");
                params.put("dob", "1511-5-15");
                params.put("gender", "male");
                params.put("zone", "lumbini");
                params.put("district", "reupedehi");
                params.put("location", "butwal");
                params.put("full_name", "Bpn");

                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(jsonStringRequest);

    }

}
