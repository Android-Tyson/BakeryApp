package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.urbangirlbakeryandroidapp.alignstech.fragment_profile.MyProfile;
import com.urbangirlbakeryandroidapp.alignstech.fragment_profile.Complaints;
import com.urbangirlbakeryandroidapp.alignstech.fragment_profile.MyOrder;

/**
 * Created by Dell on 2/9/2016.
 */
public class ProfileViewPagerAdapter extends FragmentStatePagerAdapter{

    private Context context;
    private int count;

    public ProfileViewPagerAdapter(FragmentManager fm , Context context , int count) {
        super(fm);
        this.context = context;
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MyProfile();
            case 1:
                return new MyOrder();
            case 2:
                return new Complaints();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}