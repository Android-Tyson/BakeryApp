package com.urbangirlbakeryandroidapp.alignstech.fragment_profile;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeroProfile extends Fragment {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.indicator)
    CirclePageIndicator indicator;

    public MeroProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mero_profile, container, false);
    }


}
