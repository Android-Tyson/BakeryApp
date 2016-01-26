package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.activity.SeeMoreCategories;
import com.urbangirlbakeryandroidapp.alignstech.activity.SeeMoreGifts;
import com.urbangirlbakeryandroidapp.alignstech.activity.SingleItemDetails;
import com.urbangirlbakeryandroidapp.alignstech.adapter.CustomHorizontalCakeViewAdapter;
import com.urbangirlbakeryandroidapp.alignstech.bus.GetUrgentCakesEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.HeaderImageSliderEventBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.SomeCategoriesEventBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.SomeGiftEventBus;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetHeaderImageSlider;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetHeaderOffers;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetSomeCategories;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetSomeGifts;
import com.urbangirlbakeryandroidapp.alignstech.model.RecyclerViewModel;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

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

    @InjectView(R.id.slider)
    SliderLayout mDemoSlider;

    private List<RecyclerViewModel> urgentCakeList = new ArrayList<>();
    private List<String> someCategoryIdList = new ArrayList<>();
    private List<String> someGiftIdList = new ArrayList<>();

    private ArrayList<String> someCategoryList = new ArrayList<>();
    private ArrayList<String> someGiftList = new ArrayList<>();

    private ArrayList<String> headerImageIdList = new ArrayList<>();
    private ArrayList<String> headerImageTitleList = new ArrayList<>();


    public static HomeFragment newInstance(int position) {

        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        homeFragment.setArguments(bundle);

        return homeFragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);
        see_more_gift.setOnClickListener(this);
        see_more_categories.setOnClickListener(this);
        categories_1.setOnClickListener(this);
        categories_2.setOnClickListener(this);
        categories_3.setOnClickListener(this);
        gift_1.setOnClickListener(this);
        gift_2.setOnClickListener(this);
        gift_3.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (MyUtils.isNetworkConnected(getActivity())) {
            GetSomeCategories.parseSomeCategoriesList(Apis.some_categories_list, getActivity());
            GetSomeGifts.parseSomeCategoriesList(Apis.some_gift_list, getActivity());
            GetHeaderImageSlider.parseHeaderImageSlider(Apis.headerImageSlider_urgent_cake, getActivity());
            GetHeaderOffers.parseheaderOffers(Apis.header_offers, getActivity());
        }
        recyclerView.setAdapter(new CustomHorizontalCakeViewAdapter(getActivity(), urgentCakeList));

    }


    private void initializeDataForUrgentCake(ArrayList<String>  titleList , ArrayList<String> imageUrlList){

        for(int i = 0 ; i < titleList.size() ; i++){
            urgentCakeList.add(new RecyclerViewModel(titleList.get(i) , imageUrlList.get(i)));
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CustomHorizontalCakeViewAdapter(getActivity(), urgentCakeList));

    }


    @Subscribe
    public void getSomeCategoriesList(SomeCategoriesEventBus event) {
        JSONObject jsonObject = event.getJsonObject();
        MyUtils.showLog(jsonObject.toString());
        performJsonTaskForCategories(jsonObject);
    }

    @Subscribe
    public void getSomeGiftList(SomeGiftEventBus event) {
        JSONObject jsonObject = event.getJsonObject();
        MyUtils.showLog(jsonObject.toString());
        performJsonTaskForGifts(jsonObject);
    }

    @Subscribe
    public void getHeaderImageSlider_urgentCake(HeaderImageSliderEventBus event) {
        JSONObject jsonObject = event.getJsonObject();
        MyUtils.showLog(jsonObject.toString());
        performJsonTaskForHeaderImages(jsonObject);
    }

    @Subscribe
    public void getOffersScrollList(GetUrgentCakesEvent event) {
        JSONObject jsonObject = event.getJsonObject();
        MyUtils.showLog(jsonObject.toString());
        performJsonTaskForUrgentCakes(jsonObject);
    }

    private void performJsonTaskForCategories(JSONObject jsonObject) {

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String singleChildname = jsonObj.getString("name");
                someCategoryIdList.add(jsonObj.getString("id"));
                someCategoryList.add(singleChildname);
            }
            categories_1.setText(someCategoryList.get(0));
            categories_2.setText(someCategoryList.get(1));
            categories_3.setText(someCategoryList.get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void performJsonTaskForGifts(JSONObject jsonObject) {

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String singleChildname = jsonObj.getString("name");
                someGiftIdList.add(jsonObj.getString("id"));
                someGiftList.add(singleChildname);
            }
            gift_1.setText(someGiftList.get(0));
            gift_2.setText(someGiftList.get(1));
            gift_3.setText(someGiftList.get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void performJsonTaskForHeaderImages(JSONObject jsonObject) {

        ArrayList<String> headerImageUrlList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String headerImageId = jsonObj.getString("id");
                String headerImageTitle = jsonObj.getString("name");
                String path = jsonObj.getString("path");
                String headerImageUrl ;
                if(path.equals("null")){
                    headerImageUrl = Apis.defaultImageUrl;
                }else {
                    headerImageUrl = Apis.BASE_URL + "images/" +path;
                }
                headerImageIdList.add(headerImageId);
                headerImageTitleList.add(headerImageTitle);
                headerImageUrlList.add(headerImageUrl);
            }
            imageSliderJob(headerImageTitleList, headerImageUrlList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void performJsonTaskForUrgentCakes(JSONObject jsonObject) {

        ArrayList<String> cakeTitleList = new ArrayList<>();
        ArrayList<String> cakeUrlList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String headerImageTitle = jsonObj.getString("product_name");
                String path = jsonObj.getString("path");
                String headerImageUrl;
                if(path.equals("null")){
                    headerImageUrl = Apis.defaultImageUrl;
                }else {
                    headerImageUrl = Apis.BASE_URL + "images/" +path;
                }
                cakeTitleList.add(headerImageTitle);
                cakeUrlList.add(headerImageUrl);
            }
            initializeDataForUrgentCake( cakeTitleList , cakeUrlList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyBus.getInstance().unregister(getActivity());
        AppController.getInstance().cancelPendingRequests("HOME_SCREEN_RESPONSE");
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {   }

    @Override
    public void onPageSelected(int i) {   }

    @Override
    public void onPageScrollStateChanged(int i) {  }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

        String name = (String) baseSliderView.getBundle().get("extra");
        String productId = null;
        for (int i = 0 ; i < headerImageTitleList.size() ; i++) {
            if(name.equals(headerImageTitleList.get(i))){
                productId = headerImageIdList.get(i);
            }

        }

        if(productId != null){

            String api_name = Apis.BASE_URL + "api/product-details/" + productId;

            Intent intent = new Intent(getActivity(), SingleItemDetails.class);
            intent.putExtra("TITLE_NAME", name);
            intent.putExtra("API_NAME", api_name);
            startActivity(intent);

        }

    }

    private void imageSliderJob(ArrayList<String> imageTitle , ArrayList<String> imageUrlLink){

        HashMap<String,String> url_maps = new HashMap<>();
        for(int i = 0 ; i < imageUrlLink.size() ; i++){
            url_maps.put(imageTitle.get(i), imageUrlLink.get(i));
        }

        for(String name : url_maps.keySet()){
            // initialize a SliderLayout
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.see_more_categories){
            startActivity(new Intent(getActivity() , SeeMoreCategories.class));
        }  else if(view.getId() == R.id.see_more_gift){
            startActivity(new Intent(getActivity() , SeeMoreGifts.class));

        } else if(view.getId() == R.id.textView_categories_1){
            someItemListClickJob(someCategoryIdList.get(0) , someCategoryList.get(0));
        }else if(view.getId() == R.id.textView_categories_2){
            someItemListClickJob(someCategoryIdList.get(1) , someCategoryList.get(1));
        }else if(view.getId() == R.id.textView_categories_3){
            someItemListClickJob(someCategoryIdList.get(2) , someCategoryList.get(2));

        }else if(view.getId() == R.id.textView_gift_1){
            someItemListClickJob(someGiftIdList.get(0) , someGiftList.get(0));
        }else if(view.getId() == R.id.textView_gift_2){
            someItemListClickJob(someGiftIdList.get(1) , someGiftList.get(1));
        }else if(view.getId() == R.id.textView_gift_3){
            someItemListClickJob(someGiftIdList.get(2) , someGiftList.get(2));
        }
    }


    private void someItemListClickJob(String productId , String productName){

        if(productId != null && productName != null) {

            String API_NAME = Apis.BASE_URL + "api/products/" + productId;
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_home, All_Item_Grid_Fragment.newInstance(API_NAME)).commit();

        }
    }
}
