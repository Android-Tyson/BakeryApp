package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
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

//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Posting Please Wait");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppLog.showLog(response);
//                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.showLog(error.toString());
//                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("fb_id", MainActivity.userDetials.getFb_id());
                params.put("mobile_no", MainActivity.userDetials.getMobileNo());
                params.put("email", MainActivity.userDetials.getEmail());
                params.put("dob", MainActivity.userDetials.getDob());
                params.put("gender", MainActivity.userDetials.getGender());
                params.put("zone", MainActivity.userDetials.getZone());
                params.put("district", MainActivity.userDetials.getDistrict());
                params.put("location", MainActivity.userDetials.getLocation());
                params.put("full_name", MainActivity.userDetials.getFirstName() +" "+ MainActivity.userDetials.getLastName());

                return params;
            }
        };
        jsonStringRequest.setRetryPolicy(new DefaultRetryPolicy(40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonStringRequest);

    }
}
