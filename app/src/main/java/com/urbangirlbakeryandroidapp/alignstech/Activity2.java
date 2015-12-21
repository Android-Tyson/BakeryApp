package com.urbangirlbakeryandroidapp.alignstech;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.urbangirlbakeryandroidapp.alignstech.fragments.BakeryFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.ProfileFragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class Activity2 extends MaterialNavigationDrawer implements MaterialAccountListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_2);
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

        MaterialAccount account = new MaterialAccount(getResources(), "You're not logged in.", "Click here for facebook login."
                , R.mipmap.ic_launcher, R.drawable.drawer_bg);

        addAccount(account);
        setAccountListener(this);
        setDrawerHeaderImage(R.drawable.drawer_bg);

        // add first account
//        MaterialAccount account = new MaterialAccount(
//                getResources(), "You're not logged in.", "Click here for facebook login.", R.drawable.black_gradient, R.drawable.black_gradient);
//        addAccount(account);
//        setAccountListener(this);

//        setDrawerHeaderImage(R.drawable.drawer_bg);

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

    }

    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }
}
