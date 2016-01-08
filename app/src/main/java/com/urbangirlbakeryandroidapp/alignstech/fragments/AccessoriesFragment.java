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
public class AccessoriesFragment extends Fragment {


    public AccessoriesFragment() {
        // Required empty public constructor
    }

    public static AccessoriesFragment newInstance(int position){

        AccessoriesFragment accessoriesFragment = new AccessoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION" , position);
        accessoriesFragment.setArguments(bundle);

        return accessoriesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accessories, container, false);
    }


}