package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetErrorEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetNoticeEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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

//                MyUtils.showToast(context, error.toString());
                MyBus.getInstance().post(new GetErrorEvent(error.toString()));

            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
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
