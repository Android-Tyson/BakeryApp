package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.urbangirlbakeryandroidapp.alignstech.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CakesFragment extends android.support.v4.app.Fragment {

    private MaterialDialog materialDialog;

    public CakesFragment() {
        // Required empty public constructor
    }

    public static CakesFragment newInstance(int position){

        CakesFragment cakesFragment = new CakesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION" , position);
        cakesFragment.setArguments(bundle);

        return cakesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cake, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
