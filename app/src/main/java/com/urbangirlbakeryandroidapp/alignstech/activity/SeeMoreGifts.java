package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.CustomListItemAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.SeeAllGiftsEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetAllGifts;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SeeMoreGifts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.listView)
    ListView listView;

    private ArrayList<String> childIdList = new ArrayList<>();
    private ArrayList<String> childNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_gifts);
        MyBus.getInstance().register(this);
        ButterKnife.inject(this);
        initializeToolbar();
        parseAllGifts();

        listView.setOnItemClickListener(this);
    }

    private void initializeToolbar() {

        toolbar.setTitle(R.string.gifts);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void parseAllGifts(){
//        if(MyUtils.isNetworkConnected(this)){
            GetAllGifts.parseAllGiftList(Apis.see_all_gifts, this);
//        }
    }

    @Subscribe
    public void seeAllGifts(SeeAllGiftsEvent event){

        JSONObject jsonObject = event.getJsonObject();
        performJsonTaskForGifts(jsonObject);

    }

    private void performJsonTaskForGifts(JSONObject jsonObject) {

        ArrayList<String> giftChildList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String singleChildname = jsonObj.getString("name");
                String singleChildId = jsonObj.getString("id");
                giftChildList.add(singleChildname);
                childIdList.add(singleChildId);
                childNameList.add(singleChildname);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setAdapter(new CustomListItemAdapter(this, giftChildList));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String API_NAME = Apis.BASE_URL + "api/products/" + childIdList.get(i);
        String product_title = childNameList.get(i);
//        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container_gift, GridFragment_Gift.newInstance(API_NAME, product_title)).commit();
        Intent intent = new Intent(this , GridProductGift.class);
        intent.putExtra("API", API_NAME);
        intent.putExtra("TITLE_NAME", product_title);
        intent.putExtra("SEE_GIFT", "SEE_GIFT");
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_see_all_gifts, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
        AppController.getInstance().cancelPendingRequests("GET_ALL_GIFT_TAG");
    }

}
