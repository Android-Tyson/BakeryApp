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
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends android.support.v4.app.Fragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<String> titleList, infoList;

    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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

        titleList.add("Full Name");
//        titleList.add("Last Name");
        titleList.add("Email");
        titleList.add("Primary Phone No.");
        titleList.add("Secondary Phone No.");
        titleList.add("Billing Address");
        titleList.add("Shipping Address");

        if (DataBase_Utils.isUserInfoDataExists()) {
            List<DataBase_UserInfo> infoList = DataBase_Utils.getUserInfoList();

            String firstName = infoList.get(0).getFirstName() + " "+ infoList.get(0).getLastName();
//            String lastName = infoList.get(0).getLastName();
            String email = infoList.get(0).getEmail();
            String primaryPhone = infoList.get(0).getMobilePrimary();
            String secondaryPhone = infoList.get(0).getMobileSecondary();
            String billingAddress = infoList.get(0).getBillingAddress();
            String sippingAddress = infoList.get(0).getSippingAddress();

            if(firstName == null || firstName.isEmpty()){
                firstName = "Your FirstName";
            }
//            if(lastName == null || lastName.isEmpty()){
//                lastName = "Your LastName";
//            }
            if(email == null || email.isEmpty()){
                email = "Your Email";
            }
            if(primaryPhone == null || primaryPhone.isEmpty()){
                primaryPhone = "Your Primary Phone";
            }
            if(secondaryPhone == null || secondaryPhone.isEmpty()){
                secondaryPhone = "Your Secondary Phone";
            }
            if(billingAddress == null || billingAddress.isEmpty()){
                billingAddress = "Your Billing Address";
            }
            if(sippingAddress == null || sippingAddress.isEmpty()){
                sippingAddress = "Your Sipping Address";
            }


            this.infoList.add(firstName);
//            this.infoList.add(lastName);
            this.infoList.add(email);
            this.infoList.add(primaryPhone);
            this.infoList.add(secondaryPhone);
            this.infoList.add(billingAddress);
            this.infoList.add(sippingAddress);


        }

    }
}
