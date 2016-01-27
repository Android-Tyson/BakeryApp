package com.urbangirlbakeryandroidapp.alignstech;

import android.content.Intent;
import android.os.Bundle;

import com.urbangirlbakeryandroidapp.alignstech.activity.EditProfile;
import com.urbangirlbakeryandroidapp.alignstech.activity.Login;
import com.urbangirlbakeryandroidapp.alignstech.fragments.AccessoriesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.CakesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.Settings_Fragment;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_activity2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void init(Bundle bundle) {

        allowArrowAnimation();
        addMultiPaneSupport();

        MaterialAccount account;

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

        addSection(newSection(getResources().getString(R.string.home), R.mipmap.home, HomeFragment.newInstance(0)));
        addDivisor();

        if(MyUtils.isUserLoggedIn(this)){
            addSection(newSection(getResources().getString(R.string.profile), R.mipmap.profile, new Intent(this, EditProfile.class)));
            addDivisor();
        }else{
            addSection(newSection(getResources().getString(R.string.login), R.mipmap.login, new Intent(this, Login.class)));
            addDivisor();
        }

        addSection(newSection(getResources().getString(R.string.cakes), R.mipmap.cakes, new CakesFragment()));
        addDivisor();
        addSection(newSection(getResources().getString(R.string.gifts), R.mipmap.gifts, new GiftsFragment()));
        addDivisor();
        addSection(newSection(getResources().getString(R.string.offers), R.mipmap.offers, new OfferFragment()));
        addDivisor();
        addSection(newSection(getResources().getString(R.string.accessories), R.mipmap.accessories, new AccessoriesFragment()));
        addBottomSection(newSection(getResources().getString(R.string.settings), R.mipmap.settings, new Settings_Fragment()));

    }

    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

        Bundle loginBundle = new Bundle();
        loginBundle.putBoolean("isDirectLogin", true);
        Intent loginIntent = new Intent(this, Login.class);
        loginIntent.putExtras(loginBundle);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }

}

