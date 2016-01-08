package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.adapter.CustomListAdapter;
import com.urbangirlbakeryandroidapp.alignstech.model.Product;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CakesFragment extends android.support.v4.app.Fragment {

//    @InjectView(R.id.listView)
    private GridView listView;

    private static final String url = "http://api.androidhive.info/json/movies.json";
    private CustomListAdapter adapter;
    private List<Product> productList = new ArrayList<>();

    public CakesFragment() {
        // Required empty public constructor
    }

    public static CakesFragment newInstance(int number){

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
        View view = inflater.inflate(R.layout.fragment_cake, container, false);
        listView = (GridView) view.findViewById(R.id.gridView);
//        ButterKnife.inject(getActivity(), view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CustomListAdapter(getActivity() , productList);
        listView.setAdapter(adapter);
        jsonJob();
    }

    private void jsonJob() {

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        MyUtils.showLog(" ");
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Product product = new Product();
                                product.setProductName(obj.getString("title"));
                                product.setProductImageUrl(obj.getString("image"));

                                productList.add(product);
                                MyUtils.showLog(productList.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showLog(" ");
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }


}
