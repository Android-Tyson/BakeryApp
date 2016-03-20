package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.bus.PostFbUserDetailsEvent;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 12/22/2015.
 */
public class PostFacebookUserDetials {

//    public static MaterialDialog materialDialog;

    public static void postUserDetials(String url, final Context context) {

//        materialDialog = new MaterialDialog.Builder(context).content("Loading Please wait...").cancelable(false).progress(true, 0).show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.get("result").equals("success")){

                                MyBus.getInstance().post(new PostFbUserDetailsEvent(response));

                            }else if(jsonObject.get("result").equals("You are already login")){

                                MyBus.getInstance().post(new PostFbUserDetailsEvent(response));
                                MyUtils.showToast(context , "Your are already Register...");

                            }else {

                                MyUtils.showToast(context, response);

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                        MainActivity.materialDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.materialDialog.dismiss();
//                MyUtils.showToast(context, error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
//                if(DataBase_Utils.isUserInfoDataExists()){
//
//                    List<DataBase_UserInfo> queryResults = DataBase_Utils.getUserInfoList();

                    params.put("fb_id", MainActivity.userDetials.getFb_id());
//                    params.put("email", queryResults.get(0).getEmail());
//                    params.put("dob", queryResults.get(0).getDob());
//                    params.put("gender", queryResults.get(0).getGender());
//                    params.put("zone", queryResults.get(0).getZone());
//                    params.put("district", queryResults.get(0).getDistrict());
//                    params.put("location", queryResults.get(0).getLocation());
                    params.put("full_name", MainActivity.userDetials.getFirstName() + " " +
                            MainActivity.userDetials.getLastName());

//                }

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonStringRequest, "POST_TAG");

    }
}
