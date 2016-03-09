package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.NoticeBoardListAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetNoticeEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetNoticeBoard;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoticeBoard extends AppCompatActivity {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<String> titleList = new ArrayList<>(), descList = new ArrayList<>();
    private NoticeBoardListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        MyBus.getInstance().register(this);
        ButterKnife.inject(this);
        initializeToolbar();
        initializeRecyclerView();
        GetNoticeBoard.parseNoticeList(Apis.get_gcm_notice , this);
    }

    private void initializeToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initializeRecyclerView() {

        adapter = new NoticeBoardListAdapter(this , titleList, descList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Subscribe
    public void getNoticeObject(GetNoticeEvent event){

        try {
            JSONObject jsonObject = new JSONObject(event.getResponse());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject myOrderObj = jsonArray.getJSONObject(i);
                String message = myOrderObj.getString("title");
                titleList.add(message);
                String date = myOrderObj.getString("description");
                descList.add(date);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }
}
