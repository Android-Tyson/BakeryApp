package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.AccessoriesListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.ProductDetialsEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetAllAccessories;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProductDetials;
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

public class SingleItemDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.product_image)
    NetworkImageView iv_product_image;

    @InjectView(R.id.product_name)
    TextView tv_product_name;

    @InjectView(R.id.product_price)
    TextView tv_product_price;

    @InjectView(R.id.product_description)
    TextView tv_product_description;

    @InjectView(R.id.spinner_flavour)
    Spinner spinner_flavour;

    @InjectView(R.id.spinner_pound)
    Spinner spinner_pound;

    @InjectView(R.id.order_now)
    LinearLayout orderNow;

    private String product_price;
    private String pound;
    private String per_pound_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_detils);
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

    private void parsingJob(){
        if(MyUtils.isNetworkConnected(this)){
            GetProductDetials.parseProductDetials(getApiName() , this);
            GetAllAccessories.parseAllAccessoriesList(Apis.see_all_accessories , this);
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
                product_price = jsonObj.getString("price");
                String product_description = jsonObj.getString("description");

                String path = jsonObj.getString("path");
                String product_image_url ;
                if(path.equals("null")){
                    product_image_url = Apis.defaultImageUrl;
                }else {
                    product_image_url = Apis.BASE_URL + "images/" +path;
                }

                JSONArray flavorArray = jsonObj.getJSONArray("flavor");
                ArrayList<String> flavour = new ArrayList<>();
                flavour.add("Select Flavour");

                for(int j = 0 ; j < flavorArray.length() ; j++){
                    JSONObject flavorObject = flavorArray.getJSONObject(i);
                    String flavor_id = flavorObject.getString("id");
                    String flavor = flavorObject.getString("flavor");
                    per_pound_price = flavorObject.getString("per_pound_price");
                    flavour.add(flavor);
                }
                setDataToSpinner(flavour , starting_pound , ending_pound);
                tv_product_name.setText(product_name);
                tv_product_price.setText(product_price);
                tv_product_description.setText(product_description);
                iv_product_image.setImageUrl(product_image_url , AppController.getInstance().getImageLoader());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setDataToSpinner(ArrayList<String> flavour , String starting_pounds , String ending_pound){

        int start_pound = Integer.parseInt(starting_pounds);
        int end_pound = Integer.parseInt(ending_pound);

        ArrayList<String> pound = new ArrayList<>();
        pound.add("Select Pound");
        for(int i = start_pound ; i < end_pound ; i++ ){
            pound.add(i+"");
        }

        spinner_flavour.setAdapter(new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , flavour));
        spinner_pound.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pound));

        spinner_flavour.setOnItemSelectedListener(this);
        spinner_pound.setOnItemSelectedListener(this);

    }

    private  double priceCalculation(){

        double base_price = Double.parseDouble(product_price);
        double pound_ = Double.parseDouble(pound);
        double perPoundPrice = Double.parseDouble(per_pound_price);

        return  base_price + (pound_ * perPoundPrice);

    }

    @Subscribe
    public void getAllAccessories(AccessoriesListResultEvent event){

        performJsonTaskForAccessories(event.getJsonObject());

    }

    private void performJsonTaskForAccessories(JSONObject jsonObject) {

        ArrayList<String> giftChildList = new ArrayList<>();
        String candle_price , knife_price ;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String id = jsonObj.getString("id");
                String product_name = jsonObj.getString("product_name");
                if(product_name.equals("knife")){
                    candle_price = jsonObj.getString("price");
                }else if(product_name.equals("candle")){
                    knife_price = jsonObj.getString("price");
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.spinner_flavour){
            String selectedItem = adapterView.getItemAtPosition(i).toString();
            MyUtils.showLog(" ");
        }else if(spinner.getId() == R.id.spinner_pound){
            pound = adapterView.getItemAtPosition(i).toString();
            MyUtils.showLog(" ");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        priceCalculation();
        MyUtils.showToast(this , "Your Total Price will be: "+ priceCalculation());
    }
}
