package com.urbangirlbakeryandroidapp.alignstech;

import android.content.Intent;
import android.os.Bundle;

import com.urbangirlbakeryandroidapp.alignstech.activity.Login;
import com.urbangirlbakeryandroidapp.alignstech.activity.Settings;
import com.urbangirlbakeryandroidapp.alignstech.activity.UserProfile;
import com.urbangirlbakeryandroidapp.alignstech.fragments.AccessoriesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.CakesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener {

    public static MaterialAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);

    }

    @Override
    public void init(Bundle bundle) {

        allowArrowAnimation();
        addMultiPaneSupport();
        disableLearningPattern();
        setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);

        MyUtils.setUserProfilePicture(this);
        if (MyUtils.isUserLoggedIn(this)) {

            List<DataBase_UserInfo> queryResults = DataBase_Utils.getUserInfoList();

            account = new MaterialAccount(getResources(),
                    queryResults.get(0).getFirstName() + " " + queryResults.get(0).getLastName(), ""
                    , MyUtils.getUserProfilePic(), R.drawable.drawer_bg);
        } else {
            account = new MaterialAccount(getResources(), "You're not logged in.", "Click here for facebook login."
                    , R.drawable.empty_image, R.drawable.drawer_bg);
        }

        addAccount(account);
        setAccountListener(this);
        setDrawerHeaderImage(R.drawable.drawer_bg);

        addSection(newSection(getResources().getString(R.string.home), R.mipmap.home, HomeFragment.newInstance(0)));

        if(MyUtils.isUserLoggedIn(this)){
            addSection(newSection(getResources().getString(R.string.profile), R.mipmap.profile, new Intent(this, UserProfile.class)));
        }else{
            addSection(newSection(getResources().getString(R.string.login), R.mipmap.login, new Intent(this, Login.class)));
        }

        addSection(newSection(getResources().getString(R.string.cakes), R.mipmap.cakes, new CakesFragment()));
        addSection(newSection(getResources().getString(R.string.gifts), R.mipmap.gifts, new GiftsFragment()));
        addSection(newSection(getResources().getString(R.string.offers), R.mipmap.offers, new OfferFragment()));
        addSection(newSection(getResources().getString(R.string.accessories), R.mipmap.accessories, new AccessoriesFragment()));

        addBottomSection(newSection(getResources().getString(R.string.settings), R.mipmap.settings, new Intent(this, Settings.class)));

    }


    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

        if(!MyUtils.isUserLoggedIn(this)){
            Bundle loginBundle = new Bundle();
            loginBundle.putBoolean("isDirectLogin", true);
            Intent loginIntent = new Intent(this, Login.class);
            loginIntent.putExtras(loginBundle);
            startActivity(loginIntent);
//            finish();
        }
    }


    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }


    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

}

