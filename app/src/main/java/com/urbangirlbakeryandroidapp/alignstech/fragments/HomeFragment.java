package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    @InjectView(R.id.textView_categories_1)
    TextView categories_1;

    @InjectView(R.id.textView_categories_2)
    TextView categories_2;

    @InjectView(R.id.textView_categories_3)
    TextView categories_3;

    @InjectView(R.id.see_more_categories)
    RelativeLayout see_more_categories;

    @InjectView(R.id.textView_gift_1)
    TextView gift_1;

    @InjectView(R.id.textView_gift_2)
    TextView gift_2;

    @InjectView(R.id.textView_gift_3)
    TextView gift_3;

    @InjectView(R.id.see_more_gift)
    RelativeLayout see_more_gift;

    public static HomeFragment newInstance(int position){

        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION" , position);
        homeFragment.setArguments(bundle);

        return homeFragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this , view);
        return view;
    }
}
