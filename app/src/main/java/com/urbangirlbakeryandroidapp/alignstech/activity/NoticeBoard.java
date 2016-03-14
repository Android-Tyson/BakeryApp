package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.NoticeBoardListAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetErrorEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetNoticeEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetNoticeBoard;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoticeBoard extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static MaterialDialog materialDialog;

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
        materialDialog = new MaterialDialog.Builder(this).content("Loading Please wait...").cancelable(false).progress(true , 0).show();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myAccentColor));
        swipeRefreshLayout.setOnRefreshListener(this);
        GetNoticeBoard.parseNoticeList(Apis.get_gcm_notice );
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

        if (materialDialog.isShowing())
            materialDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);

        if(titleList.size() > 0){
            titleList.clear();
            descList.clear();
        }//

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

    @Subscribe
    public void onResponseError(GetErrorEvent event) {

        if (materialDialog.isShowing())
            materialDialog.dismiss();
        MyUtils.showToast(this, event.getError());
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }

    @Override
    public void onRefresh() {

        GetNoticeBoard.parseNoticeList(Apis.get_gcm_notice );

    }
}
