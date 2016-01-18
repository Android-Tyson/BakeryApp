package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.AllItemsResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.model.Product;
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

    public static void parseAppItems(final Context context , String url){

        final JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Product> productList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Product product = new Product();
                                product.setProductName(obj.getString("title"));
                                product.setProductImageUrl(obj.getString("image"));

                                productList.add(product);
                                MyUtils.showLog(productList.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }finally {
                                MyBus.getInstance().post(new AllItemsResultEvent(productList));
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(context, "Please Check your internet connection and try again..");
            }
        });

        AppController.getInstance().addToRequestQueue(movieReq , "GET_ALL_ITEMS");
    }
}
