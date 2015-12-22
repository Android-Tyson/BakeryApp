package com.urbangirlbakeryandroidapp.alignstech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppLog;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppToast;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.button_continueWithoutLogin)
    Button btnContinueWithourLogin;

    @InjectView(R.id.btn_loginWithFacebook)
    Button btnLoginWithFacebook;

    // Generate KeyHash Reference
    // http://stackoverflow.com/questions/5306009/facebook-android-generate-key-hash

    private SimpleFacebook simpleFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        btnContinueWithourLogin.setOnClickListener(this);
        btnLoginWithFacebook.setOnClickListener(this);

        Permission[] permissions = new Permission[] {
                Permission.USER_PHOTOS,
                Permission.EMAIL,
                Permission.PUBLISH_ACTION
        };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getResources().getString(R.string.app_id))
                .setNamespace("BakeryApp")
                .setPermissions(permissions)
                .build();

        simpleFacebook.setConfiguration(configuration);

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

                if (AppUtils.isNetworkConnected(this)) {

                    simpleFacebook.login(onLoginListener);

                } else {
                    AppToast.showToast(this , "Please Check your Internet Connection And try again...");
                }

                break;
        }
    }

    OnLoginListener onLoginListener = new OnLoginListener() {

        @Override
        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
            // change the state of the button or do whatever you want
            AppLog.showLog("Logged in");
        }

        @Override
        public void onCancel() {
            // user canceled the dialog
        }

        @Override
        public void onFail(String reason) {
            // failed to login
        }

        @Override
        public void onException(Throwable throwable) {
            // exception from facebook
        }

    };

//    Logging Out
//
//    OnLogoutListener onLogoutListener = new OnLogoutListener() {
//
//        @Override
//        public void onLogout() {
//            AppLog.showLog("You are logged out");
//        }
//
//    };
//     simpleFacebook.logout(onLogoutListener);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        simpleFacebook.onActivityResult(requestCode, resultCode, data);
        AppLog.showLog(data.toString());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        simpleFacebook = SimpleFacebook.getInstance(this);
    }

}
