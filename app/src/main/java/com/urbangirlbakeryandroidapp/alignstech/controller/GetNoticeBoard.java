package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetErrorEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetNoticeEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONObject;

/**
 * Created by Dell on 1/5/2016.
 */
public class GetNoticeBoard {

    public static void parseNoticeList(String url , final Context context) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        MyBus.getInstance().post(new GetNoticeEvent(response.toString()));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                MyUtils.showToast(context, error.toString());
                MyBus.getInstance().post(new GetErrorEvent(error.toString()));

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "PRODUCT_DETAILS");

    }


//    public static void parseNoticeList(String url, final Context context) {
//
//
//        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true, 0).show();
//
//        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        MyBus.getInstance().post(new GetNoticeEvent(response));
//                        materialDialog.dismiss();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                MyUtils.showToast(context, error.toString());
//                materialDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<>();
//
//                params.put("fb_id", MyUtils.getDataFromPreferences(context, "USER_ID"));
//                MyUtils.showLog(" ");
//
//                return params;
//            }
//        };
//        jsonStringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(jsonStringRequest);

//    }

}
