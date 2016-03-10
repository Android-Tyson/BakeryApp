package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.urbangirlbakeryandroidapp.alignstech.bus.GetErrorEvent;
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
public class HomeFragment extends android.support.v4.app.Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<String> headerImageIdList = new ArrayList<>();
    private ArrayList<String> headerImageTitleList = new ArrayList<>();

    private List<String> someCategoryIdList = new ArrayList<>();
    private List<String> someGiftIdList = new ArrayList<>();
    private ArrayList<String> someCategoryList = new ArrayList<>();
    private ArrayList<String> someGiftList = new ArrayList<>();

    public static ArrayList<String> urgentCakeName = new ArrayList<>();
    public static ArrayList<String> urgentCakeId = new ArrayList<>();

    public static HashMap<String, Boolean> isGift = new HashMap<>();

    private MaterialDialog materialDialog;
//    private static boolean isImageLoaded = true;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        materialDialog = new MaterialDialog.Builder(getActivity()).content("Loading Please wait...").cancelable(false).progress(true, 0).show();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myAccentColor));
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            if (MyUtils.isNetworkConnected(getActivity())) {

                GetHeaderImageSlider.parseHeaderImageSlider(Apis.headerImageSlider_urgent_cake, getActivity());
                doParsingJob();

            }
        }
        initializeUrgentCakeRecyclerView();
    }


    private void doParsingJob() {

        GetSomeCategories.parseSomeCategoriesList(Apis.some_categories_list, getActivity());
        GetSomeGifts.parseSomeCategoriesList(Apis.some_gift_list, getActivity());
        GetHeaderOffers.parseheaderOffers(Apis.header_offers, getActivity());

    }


    private void initializeUrgentCakeRecyclerView() {

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }


    private void initializeDataForUrgentCake(ArrayList<String> titleList, ArrayList<String> imageUrlList) {

        List<RecyclerViewModel> urgentCakeList = new ArrayList<>();

        for (int i = 0; i < titleList.size(); i++) {
            urgentCakeList.add(new RecyclerViewModel(titleList.get(i), imageUrlList.get(i)));
        }
        initializeUrgentCakeRecyclerView();
        CustomHorizontalCakeViewAdapter adapter = new CustomHorizontalCakeViewAdapter(getActivity(), urgentCakeList);
        recyclerView.setAdapter(adapter);
    }


    @Subscribe
    public void getSomeCategoriesList(SomeCategoriesEventBus event) {

        JSONObject jsonObject = event.getJsonObject();
        MyUtils.showLog(jsonObject.toString());
        performJsonTaskForCategories(jsonObject);
        if (materialDialog.isShowing())
            materialDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Subscribe
    public void getSomeGiftList(SomeGiftEventBus event) {
        JSONObject jsonObject = event.getJsonObject();
        MyUtils.showLog(jsonObject.toString());
        performJsonTaskForGifts(jsonObject);
        if (materialDialog.isShowing())
            materialDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Subscribe
    public void getHeaderImageSlider_urgentCake(HeaderImageSliderEventBus event) {

        JSONObject jsonObject = event.getJsonObject();
//        if(isImageLoaded){

        performJsonTaskForHeaderImages(jsonObject);
//            isImageLoaded = false;

//        }

        if (materialDialog.isShowing())
            materialDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);

    }


    @Subscribe
    public void getOffersScrollList(GetUrgentCakesEvent event) {
        JSONObject jsonObject = event.getJsonObject();
        MyUtils.showLog(jsonObject.toString());
        performJsonTaskForDealsAndOffers(jsonObject);
        if (materialDialog.isShowing())
            materialDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
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

            if (!headerImageIdList.isEmpty()) {
                headerImageIdList.clear();
                headerImageTitleList.clear();
                headerImageUrlList.clear();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String headerImageId = jsonObj.getString("id");
                String headerImageTitle = jsonObj.getString("name");
                String path = jsonObj.getString("path");
                String headerImageUrl;
                if (path.equals("null")) {
                    headerImageUrl = Apis.defaultImageUrl;
                } else {
                    headerImageUrl = Apis.BASE_URL + "images/" + path;
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


    private void performJsonTaskForDealsAndOffers(JSONObject jsonObject) {

        ArrayList<String> urgentCakeIdList = new ArrayList<>();
        ArrayList<String> urgentCakeTitleList = new ArrayList<>();
        ArrayList<String> urgentCakeUrlList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String id = jsonObj.getString("id");
                String headerImageTitle = jsonObj.getString("product_name");
                String path = jsonObj.getString("path");
                String headerImageUrl;
                if (path.equals("null")) {
                    headerImageUrl = Apis.defaultImageUrl;
                } else {
                    headerImageUrl = Apis.BASE_URL + "images/" + path;
                }
                urgentCakeIdList.add(id);
                urgentCakeTitleList.add(headerImageTitle);
                urgentCakeUrlList.add(headerImageUrl);
            }
            initializeDataForUrgentCake(urgentCakeTitleList, urgentCakeUrlList);
            urgentCakeName = urgentCakeTitleList;
            urgentCakeId = urgentCakeIdList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }


    @Override
    public void onPageSelected(int i) {
    }


    @Override
    public void onPageScrollStateChanged(int i) {
    }


    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

        String name = (String) baseSliderView.getBundle().get("extra");
        String productId = null;
        for (int i = 0; i < headerImageTitleList.size(); i++) {
            if (name.equals(headerImageTitleList.get(i))) {
                productId = headerImageIdList.get(i);
            }

        }

        if (productId != null) {

            String api_name = Apis.BASE_URL + "api/product-details/" + productId;

            Intent intent = new Intent(getActivity(), SingleItemDetails.class);
            intent.putExtra("TITLE_NAME", name);
            intent.putExtra("API_NAME", api_name);
            startActivity(intent);

        }

    }

    private void imageSliderJob(ArrayList<String> imageTitle, ArrayList<String> imageUrlLink) {

        HashMap<String, String> url_maps = new HashMap<>();
        if (!url_maps.isEmpty())
            url_maps.clear();
        for (int i = 0; i < imageUrlLink.size(); i++) {
            url_maps.put(imageTitle.get(i), imageUrlLink.get(i));
        }

        for (String name : url_maps.keySet()) {
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

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(7000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        if (!someGiftList.isEmpty() && !someCategoryList.isEmpty()) {
            if (view.getId() == R.id.see_more_categories) {
                startActivity(new Intent(getActivity(), SeeMoreCategories.class));
            } else if (view.getId() == R.id.see_more_gift) {
                startActivity(new Intent(getActivity(), SeeMoreGifts.class));

            } else if (view.getId() == R.id.textView_categories_1) {
                someItemListClickJob(someCategoryIdList.get(0), someCategoryList.get(0));
            } else if (view.getId() == R.id.textView_categories_2) {
                someItemListClickJob(someCategoryIdList.get(1), someCategoryList.get(1));
            } else if (view.getId() == R.id.textView_categories_3) {
                someItemListClickJob(someCategoryIdList.get(2), someCategoryList.get(2));

            } else if (view.getId() == R.id.textView_gift_1) {
                isGift.put("GiftClick", true);
                someItemListClickJob(someGiftIdList.get(0), someGiftList.get(0));
            } else if (view.getId() == R.id.textView_gift_2) {
                isGift.put("GiftClick", true);
                someItemListClickJob(someGiftIdList.get(1), someGiftList.get(1));
            } else if (view.getId() == R.id.textView_gift_3) {
                isGift.put("GiftClick", true);
                someItemListClickJob(someGiftIdList.get(2), someGiftList.get(2));
            }
        }
    }


    private void someItemListClickJob(String productId, String productName) {

        if (productId != null && productName != null) {

            String API_NAME = Apis.BASE_URL + "api/products/" + productId;
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_home, All_Item_Grid_Fragment.newInstance(API_NAME, productName)).commit();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
//        isImageLoaded = true;
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        AppController.getInstance().cancelPendingRequests("HOME_SCREEN_RESPONSE");
    }


    @Override
    public void onRefresh() {

        doParsingJob();

    }

    @Subscribe
    public void onResponseError(GetErrorEvent event) {

        if (materialDialog.isShowing())
            materialDialog.dismiss();
        MyUtils.showToast(getActivity(), event.getError());
        swipeRefreshLayout.setRefreshing(false);

    }

}