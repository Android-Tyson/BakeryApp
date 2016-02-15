package com.urbangirlbakeryandroidapp.alignstech.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.activity.EditProfile;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings_Fragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);

        onPreferenceClicked();

    }

    private void onPreferenceClicked() {
        MaterialDialog materialDialog;
        Preference prefs_edit_profile = findPreference("edit_profile");
        prefs_edit_profile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                if (MyUtils.isUserLoggedIn(getActivity())){
                    startActivity(new Intent(getActivity(), EditProfile.class));
                }else {
                    MyUtils.showToast(getActivity(), "Please Login First..");
                }

                return true;
            }
        });

        Preference prefs_logout_profile = findPreference("logout_profile");
        prefs_logout_profile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new DialogFragment().show(getFragmentManager(), "dialog_screen_tag");

                return true;
            }
        });

        Preference prefs_share_apph = findPreference("share_app");
        prefs_share_apph.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new DialogFragment().show(getFragmentManager(), "dialog_screen_tag");
                return true;
            }
        });


        Preference prefs_about_bakery_app = findPreference("about_bakery_app");
        prefs_about_bakery_app.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                MyUtils.showToast(getActivity(), "prefs_about_bakery_app Click");
                return true;
            }
        });

        Preference prefs_help_and_faqs = findPreference("help_and_faqs");
        prefs_help_and_faqs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                MyUtils.showToast(getActivity(), "prefs_help_and_faqs Click");
                return true;
            }
        });

        Preference prefs_about_aligns_techs = findPreference("about_aligns_tech");
        prefs_about_aligns_techs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                MyUtils.showToast(getActivity(), "prefs_about_aligns_techs Click");
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;
        view.setBackgroundColor(getResources().getColor(R.color.layout_background));
        return view;
    }


}
