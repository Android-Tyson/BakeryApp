package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 12/22/2015.
 */
public class PostFacebookUserDetials {

    public static MaterialDialog materialDialog;

    public static void postUserDetials(String url, final Context context) {

        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true, 0).show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                if(DataBase_Utils.isUserInfoDataExists()){
//
                    List<DataBase_UserInfo> queryResults = DataBase_Utils.getUserInfoList();
//
                    params.put("fb_id", queryResults.get(0).getFb_id());
//                    params.put("mobile_no", queryResults.get(0).getMobileNo());
//                    params.put("email", queryResults.get(0).getEmail());
//                    params.put("dob", queryResults.get(0).getDob());
//                    params.put("gender", queryResults.get(0).getGender());
//                    params.put("zone", queryResults.get(0).getZone());
//                    params.put("district", queryResults.get(0).getDistrict());
//                    params.put("location", queryResults.get(0).getLocation());
                    params.put("full_name", queryResults.get(0).getFirstName() + " " + queryResults.get(0).getLastName());
//
                }

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonStringRequest, "POST_TAG");

    }
}
