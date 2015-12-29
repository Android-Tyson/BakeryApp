package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.NavListResultEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetNavigationList;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProfilePicture;
import com.urbangirlbakeryandroidapp.alignstech.fragments.BakeryFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.CakesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.ProfileFragment;
import com.urbangirlbakeryandroidapp.alignstech.model.Cakes;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
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

    private void setUserProfilePicture(){

        List<DataBase_UserInfo> queryResults = Db_Utils.getUserInfoList();
        if(queryResults.size() > 0) {
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

        if(MyUtils.isUserLoggedIn(this)){

            List<DataBase_UserInfo> queryResults = Db_Utils.getUserInfoList();

            account = new MaterialAccount(getResources(),
                    queryResults.get(0).getFirstName() + " "+ queryResults.get(0).getLastName(), ""
                    , null , R.drawable.drawer_bg);
        }else {
            account = new MaterialAccount(getResources(), "You're not logged in.", "Click here for facebook login."
                    , R.mipmap.ic_launcher, R.drawable.drawer_bg);
        }

        addAccount(account);
        setAccountListener(this);
        setDrawerHeaderImage(R.drawable.drawer_bg);

        addSection(newSection("Home", R.mipmap.ic_launcher, new HomeFragment()));
        addSection(newSection("Home", R.mipmap.ic_launcher, new HomeFragment()));

        addSubheader("Cake");
        addSection(newSection("Cake 1", R.mipmap.ic_launcher, new HomeFragment()));
        addSection(newSection("Cake 2", R.mipmap.ic_launcher, new HomeFragment()));

        addSubheader("Gifts");
        addSection(newSection("Gifts 1", R.mipmap.ic_launcher, new BakeryFragment()));
        addSection(newSection("Gifts 2", R.mipmap.ic_launcher, new BakeryFragment()));

        addSubheader("Offers");
        addSection(newSection("Offers 1", R.mipmap.ic_launcher, new GiftsFragment()));
        addSection(newSection("Offers 2", R.mipmap.ic_launcher, new GiftsFragment()));

        addSubheader("Accessories");
        addSection(newSection("Accessories 1", R.mipmap.ic_launcher, new OfferFragment()));
        addSection(newSection("Accessories 2", R.mipmap.ic_launcher, new OfferFragment()));

//        getSectionByTitle("home").setTitle("NewTitle");

        addSubheader("Settings");
        addBottomSection(newSection("Profile", R.mipmap.ic_launcher, new ProfileFragment()));

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
    public void getCakeList(NavListResultEvent event){
        List<Cakes> cakesList = event.getCakeList();
        MyUtils.showLog(cakesList.toString());

        addSubheader("Cake");
        for (int i = 0 ; i < cakesList.size() ; i++){

            addSection(newSection(cakesList.get(i).getCategoryName() , CakesFragment.newInstance(0)));

        }

        MyUtils.showLog(cakesList.toString());

    }
}
