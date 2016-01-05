package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.NormalRegisterEventBus;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 1/5/2016.
 */
public class NormalUserRegister {

    public static ProgressDialog progressDialog;

    public static void postUserDetials(String url, final Context context , final List<String> userInfo) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Posting Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyUtils.showLog(response);
                        MyUtils.saveDataInPreferences(context, "USER_LOGGED_IN", "LOGGED_IN");
                        DataBase_UserInfo dataBase_userInfo = new DataBase_UserInfo(userInfo.get(0) , userInfo.get(1) , " " , userInfo.get(2) , userInfo.get(3) , userInfo.get(4) , userInfo.get(5) , userInfo.get(6) , userInfo.get(7) , userInfo.get(8) , " ");
                        dataBase_userInfo.save();
                        MyBus.getInstance().post(new NormalRegisterEventBus(response));
                        progressDialog.dismiss();
                        
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(context , "Please Check your internet Connection and try again...");
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("fb_id", userInfo.get(0));
                params.put("mobile_no", userInfo.get(1));
                params.put("email", userInfo.get(2));
                params.put("dob", userInfo.get(3));
                params.put("gender", userInfo.get(4));
                params.put("zone", userInfo.get(5));
                params.put("district", userInfo.get(6));
                params.put("location", userInfo.get(7));
                params.put("full_name", userInfo.get(8));
                MyUtils.showLog(" ");


                return params;
            }
        };
        jsonStringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonStringRequest);

    }

}
