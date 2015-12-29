package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.NavListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MySingleton;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
            JSONArray accessoriesObject  = (JSONArray) jsonArray.get("Cakes");

            Db_Utils.deleteOldCakeListData();

            for (int i = 0 ; i < accessoriesObject.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) accessoriesObject.get(i);
                String categoryName = jsonObject1.getString("category_name");
                Cakes cakes = new Cakes(categoryName);
                cakes.save();
                MyUtils.showLog(jsonObject1.toString());
            }

            if(Db_Utils.isCakeListDataExists()) {
                List<Cakes> cakesList = Db_Utils.getCakesList();
                MyUtils.showLog(cakesList.toString());
                MyBus.getInstance().post(new NavListResultEvent(cakesList));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
