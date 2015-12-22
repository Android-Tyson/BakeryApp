package com.urbangirlbakeryandroidapp.alignstech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.appevents.AppEventsLogger;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppToast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.button_continueWithoutLogin)
    Button btnContinueWithourLogin;

    @InjectView(R.id.btn_loginWithFacebook)
    Button btnLoginWithFacebook;

    // Generate KeyHash Reference
    // http://stackoverflow.com/questions/5306009/facebook-android-generate-key-hash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        btnContinueWithourLogin.setOnClickListener(this);
        btnLoginWithFacebook.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_continueWithoutLogin:
                Intent intent = new Intent(this , HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_loginWithFacebook:
                AppToast.showToast(this , "LoginWithFacebook Job");
                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
