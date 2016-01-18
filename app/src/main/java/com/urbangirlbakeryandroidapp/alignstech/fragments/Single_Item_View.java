package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Single_Item_View extends android.support.v4.app.Fragment {


    public Single_Item_View() {
        // Required empty public constructor
    }

    public static Single_Item_View newInstance(String apiName , String itemName){

        Single_Item_View single_item_view = new Single_Item_View();
        Bundle bundle = new Bundle();
        bundle.putString("API_NAME" , apiName);
        bundle.putString("ITEM_NAME" , itemName);
        single_item_view.setArguments(bundle);
        return single_item_view;

    }

    public String getApiLink(){
        return getArguments().getString("API_NAME");
    }

    public String getTitleName(){
        return getArguments().getString("ITEM_NAME");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single__item__view, container, false);
        ButterKnife.inject(this , view);
        Toolbar toolbar = ((MainActivity)getActivity()).getToolbar();
        String titleName = getTitleName();
        toolbar.setTitle(titleName);
        return view;
    }

}
