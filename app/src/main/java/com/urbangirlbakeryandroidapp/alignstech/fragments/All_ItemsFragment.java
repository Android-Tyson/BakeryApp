package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class All_ItemsFragment extends android.support.v4.app.Fragment {


    public All_ItemsFragment() {
        // Required empty public constructor
    }

    public static All_ItemsFragment newInstance(String apiName){
        All_ItemsFragment fragObject = new All_ItemsFragment();

        Bundle args = new Bundle();
        args.putString("API", apiName);
        fragObject.setArguments(args);

        return  fragObject;
    }

    public String getApi() {
        return getArguments().getString("API");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all__items, container, false);
        ButterKnife.inject(this , view);
        String apiName = getApi();
        MyUtils.showLog(apiName);
        return view;
    }


}
