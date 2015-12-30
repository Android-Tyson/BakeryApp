package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.AccessoriesListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.CakeListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.GiftListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.OfferListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetNavigationList;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProfilePicture;
import com.urbangirlbakeryandroidapp.alignstech.fragment_profile.UserProfile;
import com.urbangirlbakeryandroidapp.alignstech.fragments.CakesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.Settings;
import com.urbangirlbakeryandroidapp.alignstech.model.Accessories;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.model.Gifts;
import com.urbangirlbakeryandroidapp.alignstech.model.Offers;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
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

        setUserProfilePicture();
        GetNavigationList.getNavList(this);

    }

    private void setUserProfilePicture() {

        List<DataBase_UserInfo> queryResults = Db_Utils.getUserInfoList();
        if (queryResults.size() > 0) {
            GetProfilePicture.getProfilePicture(this, queryResults.get(0).getProfilePicUrl());
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

        if (MyUtils.isUserLoggedIn(this)) {

            List<DataBase_UserInfo> queryResults = Db_Utils.getUserInfoList();

            account = new MaterialAccount(getResources(),
                    queryResults.get(0).getFirstName() + " " + queryResults.get(0).getLastName(), ""
                    , null, R.drawable.drawer_bg);
        } else {
            account = new MaterialAccount(getResources(), "You're not logged in.", "Click here for facebook login."
                    , R.mipmap.ic_launcher, R.drawable.drawer_bg);
        }

        addAccount(account);
        setAccountListener(this);
        setDrawerHeaderImage(R.drawable.drawer_bg);

        addSection(newSection("Home", R.mipmap.ic_launcher, new HomeFragment()));
        addSection(newSection("Profile", R.mipmap.ic_launcher, new UserProfile()));
        addBottomSection(newSection("Setting", R.mipmap.ic_launcher, new Settings()));

//        getSectionByTitle("home").setTitle("NewTitle");


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

    @Subscribe
    public void getCakeList(CakeListResultEvent event) {
        List<Cakes> cakesList = event.getCakeList();

        addDivisor();
        addSection(newSection("Cakes", R.mipmap.ic_launcher, CakesFragment.newInstance(0)));
        for (int i = 0; i < cakesList.size(); i++) {
            addSection(newSection("\t\t\t" + cakesList.get(i).getCategoryName(), CakesFragment.newInstance(0)));
        }

    }

    @Subscribe
    public void getGiftList(GiftListResultEvent event) {
        List<Gifts> giftsList = event.getGiftList();

        addDivisor();
        addSection(newSection("Gifts", R.mipmap.ic_launcher, CakesFragment.newInstance(0)));
        for (int i = 0; i < giftsList.size(); i++) {
            addSection(newSection("\t\t\t" + giftsList.get(i).getCategoryName(), CakesFragment.newInstance(0)));
        }

    }

    @Subscribe
    public void getOfferList(OfferListResultEvent event) {
        List<Offers> offerList = event.getOfferList();

        addDivisor();
        addSection(newSection("Offers", R.mipmap.ic_launcher, CakesFragment.newInstance(0)));
        for (int i = 0; i < offerList.size(); i++) {
            addSection(newSection("\t\t\t" + offerList.get(i).getCategoryName(), CakesFragment.newInstance(0)));
        }

    }

    @Subscribe
    public void getOfferList(AccessoriesListResultEvent event) {
        List<Accessories> accessoriesList = event.getAccessoriesList();

        addDivisor();
        addSection(newSection("Accessories", R.mipmap.ic_launcher, CakesFragment.newInstance(0)));
        for (int i = 0; i < accessoriesList.size(); i++) {
            addSection(newSection("\t\t\t" + accessoriesList.get(i).getCategoryName(), CakesFragment.newInstance(0)));
        }

    }
}
