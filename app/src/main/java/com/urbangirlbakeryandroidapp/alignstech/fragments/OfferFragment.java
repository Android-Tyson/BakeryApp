package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferFragment extends android.support.v4.app.Fragment {


    public OfferFragment() {
        // Required empty public constructor
    }

    public static OfferFragment newInstance(int position){

        OfferFragment offerFragment = new OfferFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION" , position);
        offerFragment.setArguments(bundle);

        return offerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer, container, false);
    }


}
