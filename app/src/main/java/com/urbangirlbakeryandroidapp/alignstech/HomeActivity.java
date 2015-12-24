package com.urbangirlbakeryandroidapp.alignstech;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProfilePicture;
import com.urbangirlbakeryandroidapp.alignstech.fragments.BakeryFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.ProfileFragment;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
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

        setUserProfilePicture();

    }

    private void setUserProfilePicture(){

        List<DataBase_UserInfo> queryResults = Db_Utils.getDatabaseList();
        GetProfilePicture.getProfilePicture(this, queryResults.get(0).getProfilePicUrl());

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

        if(MyUtils.checkDataFromPreferences(this , "") != null){

            List<DataBase_UserInfo> queryResults = new Select().from(DataBase_UserInfo.class).execute();

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
        addSection(newSection("Bakeries", R.mipmap.ic_launcher, new BakeryFragment()));

        addSubheader("Gifts");
        addSection(newSection("Name 1", R.mipmap.ic_launcher, new GiftsFragment()));
        addSection(newSection("Name 2", R.mipmap.ic_launcher, new GiftsFragment()));

        addSubheader("Offers");
        addSection(newSection("Offers 1", R.mipmap.ic_launcher, new OfferFragment()));
        addSection(newSection("Offers 2", R.mipmap.ic_launcher, new OfferFragment()));

        addSubheader("Profile");
        addSection(newSection("Profile 1", R.mipmap.ic_launcher, new ProfileFragment()));
        addSection(newSection("Profile 2", R.mipmap.ic_launcher, new ProfileFragment()));

    }


    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

        Toast.makeText(this, "onAccountOpening", Toast.LENGTH_SHORT).show();
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

}
