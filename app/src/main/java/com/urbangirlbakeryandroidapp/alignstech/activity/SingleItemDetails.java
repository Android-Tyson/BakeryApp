package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.ProductDetialsEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProductDetials;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SingleItemDetails extends AppCompatActivity {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_detils);
        ButterKnife.inject(this);
        initializeToolbar();
        MyBus.getInstance().register(this);
        parsingJob();

    }

    private void initializeToolbar() {

        toolbar.setTitle(getItemTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void parsingJob(){
        if(MyUtils.isNetworkConnected(this)){
            GetProductDetials.parseProductDetials(getApiName() , this);
        }
    }

    private String getApiName(){
        return getIntent().getStringExtra("API_NAME");
    }

    private String getItemTitle(){
        return getIntent().getStringExtra("TITLE_NAME");
    }

    @Subscribe
    public void getSingleProductDetials(ProductDetialsEvent event){

        JSONObject jsonObject = event.getJsonObject();
        performJsonTaskForGifts(jsonObject);

    }

    private void performJsonTaskForGifts(JSONObject jsonObject) {

        ArrayList<String> giftChildList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String product_id = jsonObj.getString("id");
                String product_name = jsonObj.getString("product_name");
                String starting_pound = jsonObj.getString("starting_pound");
                String ending_pound = jsonObj.getString("ending_pound");
                String price = jsonObj.getString("price");
                JSONArray flavorArray = jsonObj.getJSONArray("flavor");
                for(int j = 0 ; j < flavorArray.length() ; j++){
                    JSONObject flavorObject = flavorArray.getJSONObject(i);
                    String flavor_id = flavorObject.getString("id");
                    String flavor = flavorObject.getString("flavor");
                    String per_pound_price = flavorObject.getString("per_pound_price");
                    MyUtils.showLog(" ");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_item_detils, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
        AppController.getInstance().cancelPendingRequests("PRODUCT_DETAILS");
    }
}
