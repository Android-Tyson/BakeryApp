package com.urbangirlbakeryandroidapp.alignstech.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.activeandroid.query.Select;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 12/22/2015.
 */
public class UpdateFacebookUserDetials {

    public static ProgressDialog progressDialog ;

    public static void updateUserDetials(String url, final Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Posting Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyUtils.showLog(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showLog(error.toString());
                MyUtils.showToast(context , "Error while while updating, Please try Again..");
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                if(DataBase_Utils.isUserInfoDataExists()){

                     DataBase_UserInfo userInfo = new Select().from(DataBase_UserInfo.class).where("Fb_id = ?", "892139110900517").executeSingle();

                    MyUtils.showLog(userInfo.toString());

//                    params.put("fb_id", queryResults.get(0).getFb_id());
//                    params.put("mobile_no", queryResults.get(0).getMobileNo());
//                    params.put("email", queryResults.get(0).getEmail());
//                    params.put("dob", queryResults.get(0).getDob());
//                    params.put("gender", queryResults.get(0).getGender());
//                    params.put("zone", queryResults.get(0).getZone());
//                    params.put("district", queryResults.get(0).getDistrict());
//                    params.put("location", queryResults.get(0).getLocation());
//                    params.put("full_name", queryResults.get(0).getFirstName() + " " + queryResults.get(0).getLastName());

                }

                return params;
            }
        };
        jsonStringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonStringRequest);

    }
}
