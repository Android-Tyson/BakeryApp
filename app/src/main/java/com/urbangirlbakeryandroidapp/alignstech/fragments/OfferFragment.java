package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.CustomListItemAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.OfferListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetAllOffers;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    @InjectView(R.id.listView)
    ListView listView;

    private ArrayList<String> childIdList = new ArrayList<>();

    public OfferFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_offer, container, false);
        ButterKnife.inject(this, view);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(MyUtils.isNetworkConnected(getActivity())){
            parseAllGifts();
        }
    }

    private void parseAllGifts(){
        if(MyUtils.isNetworkConnected(getActivity())){
            GetAllOffers.parseAllOffersList(Apis.see_all_offers, getActivity());
        }
    }

    @Subscribe
    public void seeAllOffers(OfferListResultEvent event){

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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setAdapter(new CustomListItemAdapter(getActivity(), giftChildList));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(this);
        AppController.getInstance().cancelPendingRequests("GET_ALL_GIFT_TAG");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String API_NAME = Apis.BASE_URL  + "api/products/" + childIdList.get(i);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container_see_all, All_Item_Grid_Fragment.newInstance(API_NAME)).commit();

    }
}
