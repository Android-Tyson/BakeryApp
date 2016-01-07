package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.bus.AccessoriesListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.CakeListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GiftListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.OfferListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.model.Accessories;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.Gifts;
import com.urbangirlbakeryandroidapp.alignstech.model.Offers;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dell on 12/28/2015.
 */
public class GetNavigationList {

    public static ProgressDialog progressDialog;
    public static void parseNavigationDrawerList(Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Posting Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Apis.nav_collection, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyUtils.showLog(response.toString());
                jsonJob(response.toString());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showLog(error.toString());
                progressDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private static void jsonJob(String jsonObjStr){
        MyUtils.showLog(jsonObjStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonObjStr);
            JSONObject jsonObj = jsonObject.getJSONObject("result");

            // Cake Jobs
            JSONArray cakesJsonArray  = (JSONArray) jsonObj.get("Cakes");
            if(cakesJsonArray != null){

                Db_Utils.deleteOldCakeListData();

                for (int i = 0 ; i < cakesJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) cakesJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Cakes cakes = new Cakes(categoryName);
                    cakes.save();
                }

                if(Db_Utils.isCakeListDataExists()) {
                    List<Cakes> cakesList = Db_Utils.getCakesList();
                    MyBus.getInstance().post(new CakeListResultEvent(cakesList));
                }
            }


            // Gift Jobs
            JSONArray giftJsonArray  = (JSONArray) jsonObj.get("Gift");
            if(giftJsonArray != null){

                Db_Utils.deleteOldGiftListData();

                for (int i = 0 ; i < giftJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) giftJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Gifts gifts = new Gifts(categoryName);
                    gifts.save();
                }

                if(Db_Utils.isGiftListDataExists()) {
                    List<Gifts> giftList = Db_Utils.getGiftList();
                    MyBus.getInstance().post(new GiftListResultEvent(giftList));
                }
            }


            // Offers Job
            JSONArray offerJsonArray  = (JSONArray) jsonObj.get("Offers");
            if(offerJsonArray != null && !offerJsonArray.toString().isEmpty()){

                Db_Utils.deleteOldOfferListData();

                for (int i = 0 ; i < offerJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) offerJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Offers offers = new Offers(categoryName);
                    offers.save();
                }

                if(Db_Utils.isOfferListDataExists()) {
                    List<Offers> offerList = Db_Utils.getOfferList();
                    MyBus.getInstance().post(new OfferListResultEvent(offerList));
                }
            }else {
                MyUtils.showLog("NullPointerException");
            }



            // Accessories Jobs
            JSONArray accessoriesJsonArray  = (JSONArray) jsonObj.get("Accessories");
            if(accessoriesJsonArray != null){

                Db_Utils.deleteOldAccessoriesListData();

                for (int i = 0 ; i < accessoriesJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) accessoriesJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Accessories accessories = new Accessories(categoryName);
                    accessories.save();
                }

                if(Db_Utils.isAccessoriesListDataExists()) {
                    List<Accessories> accessoriesList = Db_Utils.getAccessoriesList();
                    MyBus.getInstance().post(new AccessoriesListResultEvent(accessoriesList));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
