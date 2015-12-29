package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CakesFragment extends android.support.v4.app.Fragment {


    public CakesFragment() {
        // Required empty public constructor
    }

    public CakesFragment newInstance(int number){

        CakesFragment cakesFragment = new CakesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Number" , number);
        cakesFragment.setArguments(bundle);

        return cakesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gifts, container, false);
    }


}
