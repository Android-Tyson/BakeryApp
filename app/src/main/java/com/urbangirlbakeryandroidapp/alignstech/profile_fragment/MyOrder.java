package com.urbangirlbakeryandroidapp.alignstech.profile_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.ProfileMyOrderAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetMyOrders;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostMyOrders;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;
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
public class MyOrder extends android.support.v4.app.Fragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<String> orderDateList, productNameList;
    private ProfileMyOrderAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
    }

    public MyOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_order, container, false);
        ButterKnife.inject(this, view);
        initializeRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PostMyOrders.postMyOrderList(Apis.my_orders, getActivity());
    }

    private void initializeRecyclerView() {

        orderDateList = new ArrayList<>();
        productNameList = new ArrayList<>();

        adapter = new ProfileMyOrderAdapter(getActivity(), productNameList, orderDateList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.layout_background))
                        .build());
        recyclerView.setAdapter(adapter);

    }


    @Subscribe
    public void getMyOrderObject(GetMyOrders event){

        performJsonTask(event.getJsonObject());


    }

    private void performJsonTask(JSONObject jsonObject){

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject myOrderObj = jsonArray.getJSONObject(i);
                String orderDate = myOrderObj.getString("order_date");
                orderDateList.add(orderDate);
                String productName = myOrderObj.getString("product_name");
                productNameList.add(productName);

            }
            MyUtils.showLog(" ");
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
    }
}
