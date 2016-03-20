package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.PostComplainEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 1/5/2016.
 */
public class PostUserCompains {

    private static MaterialDialog materialDialog;

    public static void postUserComplain(String url, final Context context, final ArrayList<String> postDetials) {

        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true, 0).show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.get("result").equals("success")){

                                MyBus.getInstance().post(new PostComplainEvent(response));

                            }else{
                                MyUtils.showToast(context , response);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        materialDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                MyUtils.showToast(context, error.toString());
                materialDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("user_id", postDetials.get(0));
                params.put("message", postDetials.get(1));
                params.put("address", postDetials.get(2));
                params.put("status", "0");
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
