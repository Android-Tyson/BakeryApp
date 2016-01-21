package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.activity.SingleItemDetails;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class All_Item_Grid_Fragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    @InjectView(R.id.gridView)
    GridView gridView;

    private static final String url = "http://api.androidhive.info/json/movies.json";
    private CustomGridViewAdapter adapter;
    private List<Product> productList = new ArrayList<>();

    public All_Item_Grid_Fragment() {
        // Required empty public constructor
    }

    public static All_Item_Grid_Fragment newInstance(String apiName){
        All_Item_Grid_Fragment fragObject = new All_Item_Grid_Fragment();

        Bundle args = new Bundle();
        args.putString("API", apiName);
        fragObject.setArguments(args);

        return  fragObject;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
    }

    public String getApi() {
        return getArguments().getString("API");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all__items, container, false);
        ButterKnife.inject(this, view);
        gridView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CustomGridViewAdapter(getActivity() , productList);
        gridView.setAdapter(adapter);
        if(MyUtils.isNetworkConnected(getActivity())){
            GetAllItems.parseAppItems(getActivity() , Apis.see_all_child_items);
        }
    }

    @Subscribe
    public void getAllItemList(AllItemsResultEvent event){

        productList = event.getAllItemList();
        gridView.setAdapter(new CustomGridViewAdapter(getActivity(), productList));
        adapter.notifyDataSetChanged();
        MyUtils.showLog(adapter.toString());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
        AppController.getInstance().cancelPendingRequests("GET_ALL_ITEMS");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Product product = (Product) adapterView.getAdapter().getItem(i);
        String product_id = product.getProduct_id();
        String product_name = product.getProductName();
        String api_name = Apis.BASE_URL + "api/product-details/" + product_id;

        Intent intent = new Intent(getActivity() , SingleItemDetails.class);
        intent.putExtra("TITLE_NAME" , product_name);
        intent.putExtra("API_NAME" , api_name);
        startActivity(intent);

    }
}
