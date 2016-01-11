package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetNavigationList;
import com.urbangirlbakeryandroidapp.alignstech.fragments.AccessoriesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.CakesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.Settings;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class HomeActivity extends MaterialNavigationDrawer implements MaterialAccountListener {

    public static MaterialAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);

        if(!DataBase_Utils.isCakeListDataExists()) {
            GetNavigationList.parseNavigationDrawerList(this);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init(Bundle bundle) {

        allowArrowAnimation();

        MyUtils.setUserProfilePicture(this);
        if (MyUtils.isUserLoggedIn(this)) {

            List<DataBase_UserInfo> queryResults = DataBase_Utils.getUserInfoList();

            account = new MaterialAccount(getResources(),
                    queryResults.get(0).getFirstName() + " " + queryResults.get(0).getLastName(), ""
                    , MyUtils.getUserProfilePic(), R.drawable.drawer_bg);
        } else {
            account = new MaterialAccount(getResources(), "You're not logged in.", "Click here for facebook login."
                    , R.mipmap.ic_launcher, R.drawable.drawer_bg);
        }

        addAccount(account);
        setAccountListener(this);
        setDrawerHeaderImage(R.drawable.drawer_bg);

        addSection(newSection("Home", R.mipmap.ic_launcher, HomeFragment.newInstance(0)));
        addSection(newSection("Profile", R.mipmap.ic_launcher, new Intent(this , EditProfile.class)));
        addSection(newSection("Cakes", R.mipmap.ic_launcher, CakesFragment.newInstance(0)));
        addSection(newSection("Gifts", R.mipmap.ic_launcher, GiftsFragment.newInstance(0)));
        addSection(newSection("Offers", R.mipmap.ic_launcher, OfferFragment.newInstance(0)));
        addSection(newSection("Accessories", R.mipmap.ic_launcher, AccessoriesFragment.newInstance(0)));
        addBottomSection(newSection("Setting", R.mipmap.ic_launcher, new Settings()));

//        getSectionByTitle("home").setTitle("NewTitle");

//        if(DataBase_Utils.isCakeListDataExists()) {
//            initializeSavedNavigationDrawerList();
//        }


    }

    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

        Bundle loginBundle = new Bundle();
        loginBundle.putBoolean("isDirectLogin", true);
        Intent loginIntent = new Intent(this, MainActivity.class);
        loginIntent.putExtras(loginBundle);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }


//    private void initializeSavedNavigationDrawerList(){
//
//        if(DataBase_Utils.isCakeListDataExists()){
//
//            addDivisor();
//            addSection(newSection("CAKES", R.mipmap.ic_launcher, CakesFragment.newInstance(0)));
//            List<Cakes> cakesList = DataBase_Utils.getCakesList();
//            for (int i = 0 ; i < cakesList.size() ; i++){
//                addSection(newSection(cakesList.get(i).getCategoryName() , R.mipmap.ic_drawer_blank_icon, CakesFragment.newInstance(0)));
//            }
//        }
//
//        if(DataBase_Utils.isGiftListDataExists()){
//
//            addDivisor();
//            addSection(newSection("GIFTS", R.mipmap.ic_launcher, GiftsFragment.newInstance(0)));
//            List<Gifts> giftsList = DataBase_Utils.getGiftList();
//            for (int i = 0 ; i < giftsList.size() ; i++){
//                addSection(newSection(giftsList.get(i).getCategoryName() ,R.mipmap.ic_drawer_blank_icon, GiftsFragment.newInstance(0)));
//            }
//        }
//
//        if(DataBase_Utils.isOfferListDataExists()){
//
//            addDivisor();
//            addSection(newSection("OFFERS", R.mipmap.ic_launcher, OfferFragment.newInstance(0)));
//            List<Offers> offerList = DataBase_Utils.getOfferList();
//            for (int i = 0 ; i < offerList.size() ; i++){
//                addSection(newSection(offerList.get(i).getCategoryName() , R.mipmap.ic_drawer_blank_icon, OfferFragment.newInstance(0)));
//            }
//        }
//
//        if(DataBase_Utils.isAccessoriesListDataExists()){
//
//            addDivisor();
//            addSection(newSection("ACCESSORIES", R.mipmap.ic_launcher, AccessoriesFragment.newInstance(0)));
//            List<Accessories> accessoriesList = DataBase_Utils.getAccessoriesList();
//            for (int i = 0 ; i < accessoriesList.size() ; i++){
//                addSection(newSection(accessoriesList.get(i).getCategoryName() , R.mipmap.ic_drawer_blank_icon, OfferFragment.newInstance(0)));
//            }
//        }
//
//    }

//    @Subscribe
//    public void getCakeList(CakeListResultEvent event) {
//        List<Cakes> cakesList = event.getCakeList();
//
//        addDivisor();
//        addSection(newSection("CAKES", R.mipmap.ic_launcher, CakesFragment.newInstance(0)));
//        for (int i = 0; i < cakesList.size(); i++) {
//            addSection(newSection(cakesList.get(i).getCategoryName(), R.mipmap.ic_drawer_blank_icon, CakesFragment.newInstance(0)));
//        }
//
//    }

//    @Subscribe
//    public void getGiftList(GiftListResultEvent event) {
//        List<Gifts> giftsList = event.getGiftList();
//
//        addDivisor();
//        addSection(newSection("GIFTS", R.mipmap.ic_launcher, GiftsFragment.newInstance(0)));
//        for (int i = 0; i < giftsList.size(); i++) {
//            addSection(newSection(giftsList.get(i).getCategoryName(), R.mipmap.ic_drawer_blank_icon, GiftsFragment.newInstance(0)));
//        }
//
//    }

//    @Subscribe
//    public void getOfferList(OfferListResultEvent event) {
//        List<Offers> offerList = event.getOfferList();
//
//        addDivisor();
//        addSection(newSection("OFFERS", R.mipmap.ic_launcher, OfferFragment.newInstance(0)));
//        for (int i = 0; i < offerList.size(); i++) {
//            addSection(newSection(offerList.get(i).getCategoryName(), R.mipmap.ic_drawer_blank_icon, OfferFragment.newInstance(0)));
//        }
//
//    }

//    @Subscribe
//    public void getAccessoriesList(AccessoriesListResultEvent event) {
//        List<Accessories> accessoriesList = event.getAccessoriesList();
//
//        addDivisor();
//        addSection(newSection("ACCESSORIES", R.mipmap.ic_launcher, AccessoriesFragment.newInstance(0)));
//        for (int i = 0; i < accessoriesList.size(); i++) {
//            addSection(newSection(accessoriesList.get(i).getCategoryName(), R.mipmap.ic_drawer_blank_icon, AccessoriesFragment.newInstance(0)));
//        }
//
//    }
}
