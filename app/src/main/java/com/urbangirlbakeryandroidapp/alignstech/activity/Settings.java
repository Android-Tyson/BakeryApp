package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.fragments.Settings_Fragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Settings extends AppCompatActivity {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initializeToolbar();
        getFragmentManager().beginTransaction().replace(R.id.setting_container, new Settings_Fragment()).commit();

    }

    private void initializeToolbar() {

        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
