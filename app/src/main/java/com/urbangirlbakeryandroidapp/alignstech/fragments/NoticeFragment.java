package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static MaterialDialog materialDialog;

    private List<String> titleList = new ArrayList<>(), descList = new ArrayList<>();
    private NoticeBoardListAdapter adapter;

    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeRecyclerView();
        materialDialog = new MaterialDialog.Builder(getActivity()).content("Loading Please wait...").cancelable(false).progress(true , 0).show();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myAccentColor));
        swipeRefreshLayout.setOnRefreshListener(this);
        GetNoticeBoard.parseNoticeList(Apis.get_gcm_notice, getActivity());
    }

    private void initializeRecyclerView() {

        adapter = new NoticeBoardListAdapter(getActivity() , titleList, descList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
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
                String date = myOrderObj.getString("description").trim();
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
        MyUtils.showToast(getActivity(), event.getError());
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }

    @Override
    public void onRefresh() {

        GetNoticeBoard.parseNoticeList(Apis.get_gcm_notice , getActivity() );

    }
}
