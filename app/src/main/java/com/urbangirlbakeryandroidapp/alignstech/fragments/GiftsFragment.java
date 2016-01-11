package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetNavigationList;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiftsFragment extends android.support.v4.app.Fragment {


    public GiftsFragment() {
        // Required empty public constructor
    }

    public static GiftsFragment newInstance(int position){

        GiftsFragment giftsFragment = new GiftsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION" , position);
        giftsFragment.setArguments(bundle);

        return giftsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gifts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!DataBase_Utils.isGiftListDataExists()) {
            GetNavigationList.parseNavigationDrawerList(getActivity());
        }else {

        }
    }
}
