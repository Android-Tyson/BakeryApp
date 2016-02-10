package com.urbangirlbakeryandroidapp.alignstech.profile_fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.ProfileDataListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Complaints extends android.support.v4.app.Fragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<String> titleList, infoList;

    public Complaints() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_complaints, container, false);
        ButterKnife.inject(this, view);
        initializeRecyclerView();
        return view;
    }

    private void initializeRecyclerView() {

        initializeListData();
        ProfileDataListAdapter adapter = new ProfileDataListAdapter(getActivity(), titleList, infoList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.layout_background))
                        .build());
        recyclerView.setAdapter(adapter);

    }

    private void initializeListData(){

        titleList = new ArrayList<>();
        infoList = new ArrayList<>();

        titleList.add("Complain");
        titleList.add("Complain");

        infoList.add("Address 1");
        infoList.add("Address 2");


    }

}
