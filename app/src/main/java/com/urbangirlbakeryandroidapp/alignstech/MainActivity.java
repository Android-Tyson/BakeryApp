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
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;
import com.urbangirlbakeryandroidapp.alignstech.controller.FacebookUserDetials;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.model.UserDetials;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppLog;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppToast;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.button_continueWithoutLogin)
    Button btnContinueWithoutLogin;

    @InjectView(R.id.btn_loginWithFacebook)
    Button btnLoginWithFacebook;

    public static UserDetials userDetials;
    // Generate KeyHash Reference
    // http://stackoverflow.com/questions/5306009/facebook-android-generate-key-hash

    private SimpleFacebook simpleFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        if(MyUtils.checkDataFromPreferences(getApplicationContext(), "USER_LOGGED_IN") != null &&
                !MyUtils.checkDataFromPreferences(getApplicationContext(), "USER_LOGGED_IN").isEmpty()){

            Intent intent = new Intent(this , HomeActivity.class);
            startActivity(intent);
            finish();

        }

        btnContinueWithoutLogin.setOnClickListener(this);
        btnLoginWithFacebook.setOnClickListener(this);

        Permission[] permissions = new Permission[]{
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

        switch (view.getId()) {
            case R.id.button_continueWithoutLogin:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_loginWithFacebook:

                if (MyUtils.isNetworkConnected(this)) {

                    simpleFacebook.login(onLoginListener);

                } else {
                    AppToast.showToast(this, "Please Check your Internet Connection And try again...");
                }

                break;
        }
    }

    OnLoginListener onLoginListener = new OnLoginListener() {

        @Override
        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
            // change the state of the button or do whatever you want
            AppLog.showLog("Logged in");

            PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
            pictureAttributes.setHeight(500);
            pictureAttributes.setWidth(500);
            pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);

            Profile.Properties properties = new Profile.Properties.Builder()
                    .add(Profile.Properties.ID)
                    .add(Profile.Properties.FIRST_NAME)
                    .add(Profile.Properties.LAST_NAME)
                    .add(Profile.Properties.EMAIL)
                    .add(Profile.Properties.COVER)
                    .add(Profile.Properties.BIO)
                    .add(Profile.Properties.BIRTHDAY)
                    .add(Profile.Properties.GENDER)
                    .add(Profile.Properties.HOMETOWN)
                    .add(Profile.Properties.WORK)
                    .add(Profile.Properties.LOCATION)
                    .add(Profile.Properties.PICTURE, pictureAttributes)
                    .build();

            simpleFacebook.getProfile(properties , onProfileListener);

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


    OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile profile) {
            AppLog.showLog("My profile id =" + profile.getId());

            MyUtils.saveDataInPreferences(getApplicationContext(), "USER_LOGGED_IN", "LOGGED_IN");

            userDetials = new UserDetials();

            userDetials.setFb_id(profile.getId());
            userDetials.setFirstName(profile.getFirstName());
            userDetials.setLastName(profile.getLastName());
            userDetials.setMobileNo(profile.getReligion());
            userDetials.setEmail(profile.getEmail());
            userDetials.setDob(profile.getBirthday());
            userDetials.setGender(profile.getGender());
            userDetials.setZone(profile.getLocale());
            userDetials.setDistrict(profile.getLocale());
            userDetials.setLocation(profile.getLocale());
            userDetials.setProfilePicUrl(profile.getPicture());

            handlingNullUserInfo(userDetials);

            String fb_id = userDetials.getFb_id();
            String firstName = userDetials.getFirstName();
            String lastName = userDetials.getLastName();
            String mobileNo = userDetials.getMobileNo();
            String email = userDetials.getEmail();
            String dob = userDetials.getDob();
            String gender = userDetials.getGender();
            String zone = userDetials.getZone();
            String district = userDetials.getDistrict();
            String location  = userDetials.getLocation();
            String profilePicUrl = userDetials.getProfilePicUrl();

            DataBase_UserInfo dataBase_userInfo = new DataBase_UserInfo(fb_id , firstName , lastName , mobileNo , email , dob , gender , zone , district , location , profilePicUrl);
            dataBase_userInfo.save();

            FacebookUserDetials.postUserDetials(Apis.userDetialPostURl , getApplicationContext());

            Intent intent = new Intent(getApplicationContext() , HomeActivity.class);
            intent.putExtra("UserName" , userDetials.getFirstName()+" "+userDetials.getLastName());
            startActivity(intent);
            finish();

        }

        private void handlingNullUserInfo(UserDetials userDetials){

            if(userDetials.getFb_id() == null){
                userDetials.setFb_id("Please Enter Your Id");
            }if(userDetials.getFirstName() == null){
                userDetials.setFirstName("Please Enter Your FirstName");
            } if(userDetials.getLastName() == null){
                userDetials.setLastName("Please Enter Your LastName");
            } if(userDetials.getMobileNo() == null){
                userDetials.setMobileNo("Please Enter Your MoboNo");
            } if(userDetials.getEmail() == null){
                userDetials.setEmail("Please Enter Your Email");
            } if (userDetials.getDob() == null){
                userDetials.setDob("Please Enter Your DOB");
            } if(userDetials.getGender() == null){
                userDetials.setGender("Please Enter Your Gender");
            } if (userDetials.getZone() == null){
                userDetials.setZone("Please Enter Your Zone");
            } if(userDetials.getDistrict() == null){
                userDetials.setDistrict("Please Enter Your District");
            } if(userDetials.getLocation() == null){
                userDetials.setLocation("Please Enter Your Location");
            } if(userDetials.getProfilePicUrl() == null){
                userDetials.setProfilePicUrl("Please Enter Your ProfilePicUrl");
            }

        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
        }

        @Override
        public void onThinking() {
            super.onThinking();
        }

    };

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
