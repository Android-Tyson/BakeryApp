package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.CustomGridViewAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.AllItemsResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetAllItems;
import com.urbangirlbakeryandroidapp.alignstech.model.Product;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GridProduct extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.gridView)
    GridView gridView;

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    private CustomGridViewAdapter adapter;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_cake);
        MyBus.getInstance().register(this);
        ButterKnife.inject(this);
        initializeToolbar();
        adapter = new CustomGridViewAdapter(this, productList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        doParsing();

    }

    private void initializeToolbar() {

        toolbar.setTitle(getTitleName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void doParsing() {

        if (MyUtils.isNetworkConnected(this)) {
            GetAllItems.parseAllItems(this, getApi());
        }

    }

    private String getApi() {

        return getIntent().getStringExtra("API");

    }


    private String getTitleName() {

        return getIntent().getStringExtra("TITLE_NAME");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid_cake, menu);
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

        if (item.getItemId() == android.R.id.home) {

            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void getAllItemList(AllItemsResultEvent event) {

        productList = event.getAllItemList();
        gridView.setAdapter(new CustomGridViewAdapter(this, productList));
        adapter.notifyDataSetChanged();
        MyUtils.showLog(adapter.toString());

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Product product = (Product) adapterView.getAdapter().getItem(i);
        String product_id = product.getProduct_id();
        String product_name = product.getProductName();
        String api_name = Apis.BASE_URL + "api/product-details/" + product_id;

        Intent intent = new Intent(this, SingleItemCakeDetails.class);
        intent.putExtra("TITLE_NAME", product_name);
        intent.putExtra("API_NAME", api_name);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
        AppController.getInstance().cancelPendingRequests("GET_ALL_ITEMS");
    }
}
