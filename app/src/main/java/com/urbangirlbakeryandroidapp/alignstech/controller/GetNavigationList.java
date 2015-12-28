package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 12/28/2015.
 */
public class GetNavigationList {

    public static void getNavList(Context context){

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Apis.nav_collection, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyUtils.showLog(response.toString());
                jsonJob(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showLog(error.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private static void jsonJob(String jsonObjStr){
        MyUtils.showLog(jsonObjStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonObjStr);
            JSONObject jsonArray = jsonObject.getJSONObject("result");
            JSONArray accessoriesObject  = (JSONArray) jsonArray.get("Accessories");
            for (int i = 0 ; i < accessoriesObject.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) accessoriesObject.get(i);
                String categoryName = jsonObject1.getString("category_name");
                String productname = jsonObject1.getString("product_name");


                MyUtils.showLog(jsonObject1.toString());
            }



//            Object accessoriesObject  = jsonArray.get("Accessories");
//            Object giftObject  = jsonArray.get("Gift");
//            Object offersObject  = jsonArray.get("Offers");
            MyUtils.showLog(jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
