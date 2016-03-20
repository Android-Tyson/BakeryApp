package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.AllItemsResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetErrorEvent;
import com.urbangirlbakeryandroidapp.alignstech.model.Product;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 1/18/2016.
 */
public class GetAllItems {

//    public static MaterialDialog materialDialog;

    public static void parseAllItems(final Context context, String url) {
//        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true, 0).show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        List<Product> productList = new ArrayList<>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Product product = new Product();
                                    String imageUrl;
                                    if (obj.getString("thumb").equals("null")) {
                                        imageUrl =  Apis.defaultImageUrl;
                                    } else {
                                        product.setProductName(obj.getString("product_name"));
                                        imageUrl = Apis.BASE_URL + "images/" + obj.getString("thumb");
                                        product.setProduct_id(obj.getString("id"));

                                    }
                                    product.setProductImageUrl(imageUrl);

                                    productList.add(product);
                                    MyUtils.showLog(productList.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } finally {
                                    MyBus.getInstance().post(new AllItemsResultEvent(productList));
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        materialDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                MyUtils.showToast(context, error.toString());
//                materialDialog.dismiss();
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
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "GET_ALL_ITEMS");
    }
}
