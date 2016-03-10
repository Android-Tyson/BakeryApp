package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.CustomHorizontalAccessoriesAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.AccessoriesListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.CheckBoxEventBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.CheckBoxFalseEventBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetErrorEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetOpeningClosingEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.OrderEventBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.ProductDetialsEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.UserDetailsListEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetAllAccessories;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetOpeningClosingDate;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProductDetials;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostOrderProduct;
import com.urbangirlbakeryandroidapp.alignstech.fragments.Ordered_Cake_Details;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SingleItemDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.product_image)
    NetworkImageView iv_product_image;

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

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.eggless)
    CheckBox eggless;

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private String product_price = "0.00", pound = "0.00", per_pound_price = "0.00";
    private Double totalPrice = 0.00, eggless_price = 0.00;

    private ArrayList<String> accessoryIdList = new ArrayList<>();
    private ArrayList<String> accessoryNameList = new ArrayList<>();
    private ArrayList<String> accessoriesPriceList = new ArrayList<>();
    private ArrayList<String> per_pound_price_list = new ArrayList<>();
    private ArrayList<String> flavourList = new ArrayList<>();

    private HashMap<Integer, Integer> checkedPosition = new HashMap<>();

    private CustomHorizontalAccessoriesAdapter adapter;
    private double accessoriesTotalPrice = 0.00;

    private ArrayList<String> singleProductDetailsList = new ArrayList<>();
    private ArrayList<String> orderedUserDetails;
    private String selectedFlavor, selectedFlovourId;
    private boolean isEggless = false;
    private boolean isUrgent = false;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_detils);
        ButterKnife.inject(this);
        initializeToolbar();
        adapter = new CustomHorizontalAccessoriesAdapter(this, accessoryNameList);
        MyBus.getInstance().register(this);
        parsingJob();
        orderNow.setOnClickListener(this);
        initializeDataForAccessories();
        eggless.setOnCheckedChangeListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myAccentColor));
        swipeRefreshLayout.setOnRefreshListener(this);
        materialDialog = new MaterialDialog.Builder(this).content("Loading Please wait...").cancelable(false).progress(true, 0).show();

    }

    private void initializeToolbar() {

        toolbar.setTitle(getItemTitle());
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void parsingJob() {
        if (MyUtils.isNetworkConnected(this)) {
            GetProductDetials.parseProductDetials(getApiName(), this);
            GetAllAccessories.parseAllAccessoriesList(Apis.see_all_accessories, this);
            GetOpeningClosingDate.getOpeningClosingDate(Apis.get_opening_closing, this);

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


        if (materialDialog.isShowing())
            materialDialog.dismiss();
        JSONObject jsonObject = event.getJsonObject();
        performJsonTaskForSingleProductDetails(jsonObject);

        swipeRefreshLayout.setRefreshing(false);
    }


    @Subscribe
    public void onResponseError(GetErrorEvent event) {

        if (materialDialog.isShowing())
            materialDialog.dismiss();
        MyUtils.showToast(this, event.getError());
        swipeRefreshLayout.setRefreshing(false);

    }


    private void performJsonTaskForSingleProductDetails(JSONObject jsonObject) {

        try {
            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
            JSONObject jsonObj = jsonObject1.getJSONObject("result");
            String product_id = jsonObj.getString("id");
            String product_name = jsonObj.getString("product_name");
            String starting_pound = jsonObj.getString("starting_pound");
            String ending_pound = jsonObj.getString("ending_pound");
            product_price = jsonObj.getString("price");
            String product_description = jsonObj.getString("description");
            String default_flavour_id = jsonObj.getString("default_flavor");
            if(jsonObj.getString("is_active").equals("2"))
                isUrgent = true;


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


            JSONObject flavorObj = jsonObj.getJSONObject("flavor");
            ArrayList<String> flavour = new ArrayList<>();

            for (int j = 0; j < flavorObj.length() - 1; j++) {
                JSONObject flavorObject = flavorObj.getJSONObject(String.valueOf(j));
                String flavor_id = flavorObject.getString("id");
                if (flavor_id.equals(default_flavour_id)) {
                    per_pound_price_list.add(flavorObject.getString("per_pound_price"));
                    flavour.add(flavorObject.getString("flavor"));

                }
            }

            for (int j = 0; j < flavorObj.length() - 1; j++) {
                JSONObject flavorObject = flavorObj.getJSONObject(String.valueOf(j));
                String flavor_id = flavorObject.getString("id");
                if (!flavor_id.equals(default_flavour_id)) {
                    String flavor = flavorObject.getString("flavor");
                    per_pound_price_list.add(flavorObject.getString("per_pound_price"));
                    flavourList.add(flavorObject.getString("id"));
                    flavour.add(flavor);
                }
            }
            setDataToSpinner(flavour, starting_pound, ending_pound);
            tv_product_price.setText(product_price);
            tv_product_description.setText(product_description);
            iv_product_image.setImageUrl(product_image_url, AppController.getInstance().getImageLoader());
//            }
        } catch (JSONException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }


    private void setDataToSpinner(ArrayList<String> flavour, String starting_pounds, String ending_pound) {

        double start_pound = Double.parseDouble(starting_pounds);
        double end_pound = Double.parseDouble(ending_pound);

        ArrayList<String> pound = new ArrayList<>();
        for (double i = start_pound; i <= end_pound; i++) {
            pound.add(i + "");
        }

        ArrayList<String> flavour_list = new ArrayList<>();
        for (int i = 0; i < flavour.size(); i++) {
            flavour_list.add(flavour.get(i));
        }

        spinner_flavour.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, flavour_list));
        spinner_pound.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pound));

        spinner_flavour.setOnItemSelectedListener(this);
        spinner_pound.setOnItemSelectedListener(this);

    }


    @Subscribe
    public void getAllAccessories(AccessoriesListResultEvent event) {

        performJsonTaskForAccessories(event.getJsonObject());

    }


    private void performJsonTaskForAccessories(JSONObject jsonObject) {

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if (!accessoryNameList.isEmpty())
                accessoryNameList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String id = jsonObj.getString("id");
                String product_name = jsonObj.getString("product_name");
                String price = jsonObj.getString("price");
                accessoryIdList.add(id);
                accessoriesPriceList.add(price);
                accessoryNameList.add(product_name);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initializeDataForAccessories();
    }

    private void initializeDataForAccessories() {


        LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        recyclerView.setAdapter(adapter);

    }


    private void orderSelectedProduct() {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {

            for (int i = 0; i < accessoryIdList.size(); i++) {
                JSONObject jsonObjSingleProduct = new JSONObject();

                switch (i) {
                    case 0:
                        jsonObjSingleProduct.put("order_id", "11111");
                        jsonObjSingleProduct.put("product_id", singleProductDetailsList.get(0));
                        jsonObjSingleProduct.put("product_name", singleProductDetailsList.get(1));
                        jsonObjSingleProduct.put("price", singleProductDetailsList.get(2));
                        jsonObjSingleProduct.put("qty", "1");
                        jsonArray.put(jsonObjSingleProduct);

                        break;
                }

                JSONObject jsonObject2 = new JSONObject();
                if (checkedPosition.get(i) != null) {
                    int position = checkedPosition.get(i);
                    switch (position) {
                        case 0:
                            jsonObject2.put("order_id", "11111");
                            jsonObject2.put("product_id", accessoryIdList.get(0));
                            jsonObject2.put("price", accessoriesPriceList.get(0));
                            accessoriesTotalPrice += Double.parseDouble(accessoriesPriceList.get(0));
                            jsonObject2.put("product_name", accessoryNameList.get(0));
                            jsonObject2.put("qty", "1");
                            jsonArray.put(jsonObject2);
                            break;
                        case 1:
                            jsonObject2.put("order_id", "11111");
                            jsonObject2.put("product_id", accessoryIdList.get(1));
                            jsonObject2.put("price", accessoriesPriceList.get(1));
                            accessoriesTotalPrice += Double.parseDouble(accessoriesPriceList.get(1));
                            jsonObject2.put("product_name", accessoryNameList.get(1));
                            jsonObject2.put("qty", "1");
                            jsonArray.put(jsonObject2);
                            break;
                        case 2:
                            jsonObject2.put("order_id", "11111");
                            jsonObject2.put("product_id", accessoryIdList.get(2));
                            jsonObject2.put("price", accessoriesPriceList.get(2));
                            accessoriesTotalPrice += Double.parseDouble(accessoriesPriceList.get(2));
                            jsonObject2.put("product_name", accessoryNameList.get(2));
                            jsonObject2.put("qty", "1");
                            jsonArray.put(jsonObject2);
                            break;
                        case 3:
                            jsonObject2.put("order_id", "11111");
                            jsonObject2.put("product_id", accessoryIdList.get(3));
                            jsonObject2.put("price", accessoriesPriceList.get(3));
                            accessoriesTotalPrice += Double.parseDouble(accessoriesPriceList.get(3));
                            jsonObject2.put("product_name", accessoryNameList.get(3));
                            jsonObject2.put("qty", "1");
                            jsonArray.put(jsonObject2);
                            break;
                    }

                }
            }

            jsonObject.put("contact_person_name", orderedUserDetails.get(0));
            jsonObject.put("phone_no1", orderedUserDetails.get(1));
            jsonObject.put("phone_no2", orderedUserDetails.get(2));
            jsonObject.put("delivery_address", orderedUserDetails.get(3));
            jsonObject.put("message_on_cake", orderedUserDetails.get(4));
            jsonObject.put("order_date", orderedUserDetails.get(5));
            jsonObject.put("sender_address", orderedUserDetails.get(7));
            jsonObject.put("receiver_address", orderedUserDetails.get(8));

            if (MyUtils.isUserLoggedIn(this)) {

                jsonObject.put("user_id", getUserId());

            } else {

                jsonObject.put("user_id", "0");

            }
            jsonObject.put("total", totalPrice);
            jsonObject.put("flavor", selectedFlovourId);
            jsonObject.put("pound", pound);
            jsonObject.put("order_details", jsonArray);
            jsonObject.put("isEgg_less", isEggless);

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
                MyUtils.showToast(this, "Successfully Ordered and your total price is: " + totalPrice);

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        if (spinner.getId() == R.id.spinner_flavour) {
            selectedFlavor = adapterView.getItemAtPosition(i).toString();
            selectedFlovourId = flavourList.get(i);
            per_pound_price = per_pound_price_list.get(i);
            MyUtils.showLog(per_pound_price);

            totalPrice = (Double.parseDouble(product_price) + eggless_price +
                    Double.parseDouble(per_pound_price)) *
                    Double.parseDouble(pound) + accessoriesTotalPrice;
            tv_product_price.setText(String.valueOf(totalPrice));

        } else if (spinner.getId() == R.id.spinner_pound) {
            pound = adapterView.getItemAtPosition(i).toString();
            totalPrice = (Double.parseDouble(product_price) + eggless_price +
                    Double.parseDouble(per_pound_price)) *
                    Double.parseDouble(pound) + accessoriesTotalPrice;
            tv_product_price.setText(String.valueOf(totalPrice));
            MyUtils.showLog(" ");
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {

        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container,
                new Ordered_Cake_Details(), "FRAME_CONTAINER").commit();
        dialogForOpeningAndClosingTime(this);

    }


    @Subscribe
    public void getCheckedItems(CheckBoxEventBus eventBus) {

        checkedPosition.put(eventBus.getPosition(), eventBus.getPosition());
        String accessoryItemPrice = accessoriesPriceList.get(eventBus.getPosition());
        totalPrice += Double.parseDouble(accessoryItemPrice);
        accessoriesTotalPrice += Double.parseDouble(accessoryItemPrice);
        tv_product_price.setText(String.valueOf(totalPrice));

    }


    @Subscribe
    public void removeUncheckedItems(CheckBoxFalseEventBus eventBus) {

        checkedPosition.remove(eventBus.getPosition());
        String accessoryItemPrice = accessoriesPriceList.get(eventBus.getPosition());
        totalPrice -= Double.parseDouble(accessoryItemPrice);
        accessoriesTotalPrice -= Double.parseDouble(accessoryItemPrice);
        tv_product_price.setText(String.valueOf(totalPrice));

    }

    @Subscribe
    public void userDetailList(UserDetailsListEvent event) {

        orderedUserDetails = event.getUserDetailsList();
        MyUtils.showLog(orderedUserDetails.toString());
        orderSelectedProduct();

    }

    @Subscribe
    public void getOpeningClosingDate(GetOpeningClosingEvent event) {

        String response = event.getResponse();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String opening_hour = jsonObject.getString("opening_hour");
            String closing_hour = jsonObject.getString("closing_hour");
            String egg_less_price = jsonObject.getString("egg_less_price");
            MyUtils.saveDataInPreferences(this, "EGG_LESS_PRICE", egg_less_price);

            if (MyUtils.getDataFromPreferences(this, "OPENING_HOUR").isEmpty()) {
                MyUtils.saveDataInPreferences(this, "OPENING_HOUR", opening_hour);
                MyUtils.saveDataInPreferences(this, "CLOSING_HOUR", closing_hour);
            } else {
                MyUtils.editDataOfPreferences(this, "OPENING_HOUR", opening_hour);
                MyUtils.editDataOfPreferences(this, "CLOSING_HOUR", closing_hour);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private String getUserId() {

        return MyUtils.getDataFromPreferences(this, "USER_ID");

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


        if (compoundButton.isChecked()) {

            isEggless = true;
            eggless_price = Double.parseDouble(MyUtils.getDataFromPreferences(this, "EGG_LESS_PRICE"));

            totalPrice = (Double.parseDouble(product_price) + eggless_price +
                    Double.parseDouble(per_pound_price)) *
                    Double.parseDouble(pound) + accessoriesTotalPrice;

            tv_product_price.setText(String.valueOf(totalPrice));

        } else {

            isEggless = false;
            eggless_price = 0.00;

            totalPrice = (Double.parseDouble(product_price) + eggless_price +
                    Double.parseDouble(per_pound_price)) *
                    Double.parseDouble(pound) + accessoriesTotalPrice;

            tv_product_price.setText(String.valueOf(totalPrice));

        }

    }

    @Override
    public void onRefresh() {

        parsingJob();

    }

    private void dialogForOpeningAndClosingTime(final Context context) {

        if (isUrgent) {

            new MaterialDialog.Builder(context)
                    .title("Notice")
                    .content("Please order this cake before: " + MyUtils.getDataFromPreferences(context, "OPENING_HOUR")
                            + " and after: " + MyUtils.getDataFromPreferences(context, "CLOSING_HOUR") +
                            ". This is urgent cake ...... BLAH")
                    .positiveText("Ok")
                    .cancelable(false)
                    .positiveColorRes(R.color.myPrimaryColor)
                    .callback(new MaterialDialog.ButtonCallback() {

                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            dialog.dismiss();
                        }
                    })
                    .build()
                    .show();

        } else {

            new MaterialDialog.Builder(context)
                    .title("Notice")
                    .content("Please order this cake before: " + MyUtils.getDataFromPreferences(context, "OPENING_HOUR")
                            + " and after: " + MyUtils.getDataFromPreferences(context, "CLOSING_HOUR"))
                    .positiveText("Ok")
                    .cancelable(false)
                    .positiveColorRes(R.color.myPrimaryColor)
                    .callback(new MaterialDialog.ButtonCallback() {

                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            dialog.dismiss();
                        }
                    })
                    .build()
                    .show();

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

        if(id == R.id.homeAsUp){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
