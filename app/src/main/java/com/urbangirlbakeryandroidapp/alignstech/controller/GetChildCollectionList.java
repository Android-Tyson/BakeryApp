package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.AccessoriesListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.CakeListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GiftListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.OfferListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.model.Accessories;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.Gifts;
import com.urbangirlbakeryandroidapp.alignstech.model.Offers;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dell on 12/28/2015.
 */
public class GetChildCollectionList {

    private static MaterialDialog materialDialog;
    public static void parseNavigationDrawerList(final Context context){

        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").progress(true , 0).show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Apis.nav_collection, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MyUtils.showLog(response.toString());
                jsonJob(response.toString());
                materialDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(context, context.getResources().getString(R.string.network_error));
                materialDialog.dismiss();
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

                DataBase_Utils.deleteOldCakeListData();

                for (int i = 0 ; i < cakesJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) cakesJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Cakes cakes = new Cakes(categoryName);
                    cakes.save();
                }

                if(DataBase_Utils.isCakeListDataExists()) {
                    List<Cakes> cakesList = DataBase_Utils.getCakesList();
                    MyBus.getInstance().post(new CakeListResultEvent(cakesList));
                }
            }


            // Gift Jobs
            JSONArray giftJsonArray  = (JSONArray) jsonObj.get("Gift");
            if(giftJsonArray != null){

                DataBase_Utils.deleteOldGiftListData();

                for (int i = 0 ; i < giftJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) giftJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Gifts gifts = new Gifts(categoryName);
                    gifts.save();
                }

                if(DataBase_Utils.isGiftListDataExists()) {
                    List<Gifts> giftList = DataBase_Utils.getGiftList();
                    MyBus.getInstance().post(new GiftListResultEvent(giftList));
                }
            }


            // Offers Job
            JSONArray offerJsonArray  = (JSONArray) jsonObj.get("Offers");
            if(offerJsonArray != null && !offerJsonArray.toString().isEmpty()){

                DataBase_Utils.deleteOldOfferListData();

                for (int i = 0 ; i < offerJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) offerJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Offers offers = new Offers(categoryName);
                    offers.save();
                }

                if(DataBase_Utils.isOfferListDataExists()) {
                    List<Offers> offerList = DataBase_Utils.getOfferList();
                    MyBus.getInstance().post(new OfferListResultEvent(offerList));
                }
            }else {
                MyUtils.showLog("NullPointerException");
            }



            // Accessories Jobs
            JSONArray accessoriesJsonArray  = (JSONArray) jsonObj.get("Accessories");
            if(accessoriesJsonArray != null){

                DataBase_Utils.deleteOldAccessoriesListData();

                for (int i = 0 ; i < accessoriesJsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) accessoriesJsonArray.get(i);
                    String categoryName = jsonObject1.getString("category_name");
                    Accessories accessories = new Accessories(categoryName);
                    accessories.save();
                }

                if(DataBase_Utils.isAccessoriesListDataExists()) {
                    List<Accessories> accessoriesList = DataBase_Utils.getAccessoriesList();
                    MyBus.getInstance().post(new AccessoriesListResultEvent(accessoriesList));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
