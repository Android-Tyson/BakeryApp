package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.AllItemsResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.model.Product;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 1/18/2016.
 */
public class GetAllItems {

    public static MaterialDialog materialDialog;

    public static void parseAppItems(final Context context, String url) {
        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true, 0).show();

        final JsonObjectRequest movieReq = new JsonObjectRequest(url,
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
                                    if (obj.getString("path").equals("null")) {
                                        imageUrl =  Apis.defaultImageUrl;
                                    } else {
                                        product.setProductName(obj.getString("product_name"));
                                        imageUrl = Apis.BASE_URL + "images/" + obj.getString("path");

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
                        materialDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(context, error.toString());
                materialDialog.dismiss();
            }
        });

        AppController.getInstance().addToRequestQueue(movieReq, "GET_ALL_ITEMS");
    }
}
