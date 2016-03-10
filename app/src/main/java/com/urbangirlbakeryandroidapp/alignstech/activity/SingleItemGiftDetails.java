package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.OrderEventBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.OrderedGiftDetailsEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.ProductDetialsEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.UserDetailsListEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProductDetials;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostOrderProduct;
import com.urbangirlbakeryandroidapp.alignstech.fragments.Ordered_Gift_Details;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SingleItemGiftDetails extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.product_image)
    NetworkImageView iv_product_image;

    @InjectView(R.id.product_price)
    TextView tv_product_price;

    @InjectView(R.id.product_description)
    TextView tv_product_description;

    @InjectView(R.id.order_now)
    LinearLayout orderNow;

    private String product_price;

    private ArrayList<String> singleProductDetailsList = new ArrayList<>();
    private ArrayList<String> orderedUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_gift_details);
        ButterKnife.inject(this);
        initializeToolbar();
        MyBus.getInstance().register(this);
        parsingJob();
        orderNow.setOnClickListener(this);
    }

    private void initializeToolbar() {

        toolbar.setTitle(getItemTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void parsingJob() {
        if (MyUtils.isNetworkConnected(this)) {
            GetProductDetials.parseProductDetials(getApiName(), this);
        }
    }

    private String getApiName() {
        return getIntent().getStringExtra("API_NAME");
    }

    private String getItemTitle() {
        return getIntent().getStringExtra("TITLE_NAME");
    }

    @Subscribe
    public void getSingleProductDetials(ProductDetialsEvent event) {

        JSONObject jsonObject = event.getJsonObject();
        performJsonTaskForSingleProductDetails(jsonObject);

    }

    private void performJsonTaskForSingleProductDetails(JSONObject jsonObject) {

        try {
            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
            JSONObject jsonObj = jsonObject1.getJSONObject("result");
            String product_id = jsonObj.getString("id");
            String product_name = jsonObj.getString("product_name");
            product_price = jsonObj.getString("price");
            String product_description = jsonObj.getString("description");

            singleProductDetailsList.add(product_id);
            singleProductDetailsList.add(product_name);
            singleProductDetailsList.add(product_price);

            String path = jsonObj.getString("path");
            String product_image_url;
            if (path.equals("null")) {
                product_image_url = Apis.defaultImageUrl;
            } else {
                product_image_url = Apis.BASE_URL + "images/" + path;
            }

            tv_product_price.setText(product_price);
            tv_product_description.setText(product_description);
            iv_product_image.setImageUrl(product_image_url, AppController.getInstance().getImageLoader());
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private double priceCalculation() {

        return Double.parseDouble(product_price);

    }


    private void orderSelectedProduct() {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {

            for (int i = 0; i < singleProductDetailsList.size(); i++) {
                JSONObject jsonObjSingleProduct = new JSONObject();

                if (i == 0) {
                    jsonObjSingleProduct.put("order_id", "NO_ORDER_ID");
                    jsonObjSingleProduct.put("product_id", singleProductDetailsList.get(0));
                    jsonObjSingleProduct.put("product_name", singleProductDetailsList.get(1));
                    jsonObjSingleProduct.put("price", singleProductDetailsList.get(2));
                    jsonObjSingleProduct.put("qty", "1");
                    jsonArray.put(jsonObjSingleProduct);

                }
            }

            jsonObject.put("contact_person_name", orderedUserDetails.get(0));
            jsonObject.put("phone_no1", orderedUserDetails.get(1));
            jsonObject.put("phone_no2", orderedUserDetails.get(2));
            jsonObject.put("receiver_address", orderedUserDetails.get(3));
            jsonObject.put("message", orderedUserDetails.get(4));
            jsonObject.put("order_date", orderedUserDetails.get(5));
            jsonObject.put("gift_sender_name", orderedUserDetails.get(7));
            jsonObject.put("gift_receiver_name", orderedUserDetails.get(8));
            jsonObject.put("sender_address", orderedUserDetails.get(9));

            jsonObject.put("user_id", MyUtils.getDataFromPreferences(this, "USER_ID"));
            jsonObject.put("total", priceCalculation());
            jsonObject.put("order_details", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyUtils.showLog(jsonObject.toString());
        MyUtils.showLog(jsonArray.toString());

        PostOrderProduct.postOrderProduct(Apis.product_order, this, jsonObject.toString());

    }

    @Subscribe
    public void orderProductResponse(OrderEventBus eventBus) {

        try {
            JSONObject jsonObject = new JSONObject(eventBus.getResponse());
            String result = jsonObject.getString("result");
            if (result.equals("success"))
                MyUtils.showToast(this, "Successfully Ordered and your total price is: " + priceCalculation());

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Subscribe
    public void orderUserDetails(OrderedGiftDetailsEvent event) {

        orderSelectedProduct();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
        AppController.getInstance().cancelPendingRequests("PRODUCT_DETAILS");
    }


    @Override
    public void onClick(View view) {

        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container,
                new Ordered_Gift_Details(), "FRAME_CONTAINER").commit();

    }

    private void dialogIfNotLoggedIn(final Context context) {

        new MaterialDialog.Builder(this)
                .title("Please Login")
                .content("You Are not Logged In. Please login to continue..")
                .positiveText("Login")
                .negativeText("Later")
                .autoDismiss(true)
                .positiveColorRes(R.color.myPrimaryColor)
                .negativeColorRes(R.color.myPrimaryColor)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("LearningPattern", "true");
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .build()
                .show();

    }

    @Subscribe
    public void userDetailList(UserDetailsListEvent event) {

        orderedUserDetails = event.getUserDetailsList();
        MyUtils.showLog(orderedUserDetails.toString());
        orderSelectedProduct();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_item_gift_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_settings){

            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container,
                    new Ordered_Gift_Details(), "FRAME_CONTAINER").commit();

        }

        return super.onOptionsItemSelected(item);
    }
}