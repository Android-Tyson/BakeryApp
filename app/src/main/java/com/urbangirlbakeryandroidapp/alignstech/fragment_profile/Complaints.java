package com.urbangirlbakeryandroidapp.alignstech.fragment_profile;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.ProfileDataListAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetComplainEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.PostComplainEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetMyCompains;
import com.urbangirlbakeryandroidapp.alignstech.fragment_dialog.MyComplains;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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
public class Complaints extends android.support.v4.app.Fragment implements View.OnClickListener {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.postComplains)
    ImageView postComplains;

    private List<String> complainList = new ArrayList<>(), dateList = new ArrayList<>();
    private ProfileDataListAdapter adapter;

    public Complaints() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_complaints, container, false);
        ButterKnife.inject(this, view);
        initializeRecyclerView();
        postComplains.setOnClickListener(this);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetMyCompains.getUserComplain(Apis.get_complain, getActivity());
    }


    private void initializeRecyclerView() {

        adapter = new ProfileDataListAdapter(getActivity(), complainList, dateList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.layout_background))
                        .build());
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.postComplains){

            new MyComplains().show(getActivity().getSupportFragmentManager(), "welcome_screen_tag");

        }
    }


    @Subscribe
    public void getComplainObject(GetComplainEvent event){

        try {
            JSONObject jsonObject = new JSONObject(event.getResponse());
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject myOrderObj = jsonArray.getJSONObject(i);
                String message = myOrderObj.getString("message");
                complainList.add(message);
                String date = myOrderObj.getString("address");
                dateList.add(date);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            adapter.notifyDataSetChanged();
        }

    }

    @Subscribe
    public void userPostResponse(PostComplainEvent event) {

//        MyUtils.showToast(getActivity(), "Your complain is successfully posted..");
        Intent intent = new Intent(getActivity() , MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }
}
