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
import com.urbangirlbakeryandroidapp.alignstech.activity.HomeActivity;
import com.urbangirlbakeryandroidapp.alignstech.activity.NormalRegister;
import com.urbangirlbakeryandroidapp.alignstech.controller.GetProfilePicture;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostFacebookUserDetials;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.model.UserDetials;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.button_continueWithoutLogin)
    Button btnContinueWithoutLogin;

    @InjectView(R.id.btn_loginWithFacebook)
    Button btnLoginWithFacebook;

    @InjectView(R.id.btn_normal_login)
    Button btnNormalLogin;

    public static UserDetials userDetials;
    // Generate KeyHash Reference
    // http://stackoverflow.com/questions/5306009/facebook-android-generate-key-hash

    private SimpleFacebook simpleFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        if(MyUtils.isUserLoggedIn(getApplicationContext())){

            Intent intent = new Intent(this , HomeActivity.class);
            startActivity(intent);
            finish();

        }

        btnContinueWithoutLogin.setOnClickListener(this);
        btnLoginWithFacebook.setOnClickListener(this);
        btnNormalLogin.setOnClickListener(this);

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
                    MyUtils.showToast(this, "Please Check your Internet Connection And try again...");
                }
                break;

            case R.id.btn_normal_login:
                MyUtils.showLog("Clicked Direct login");
                Intent intent1 = new Intent(this , NormalRegister.class);
                startActivity(intent1);
        }
    }

    OnLoginListener onLoginListener = new OnLoginListener() {

        @Override
        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
            // change the state of the button or do whatever you want
            MyUtils.showLog("Logged in");

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
            MyUtils.showLog("My profile id =" + profile.getId());

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

            String user_fb_id = userDetials.getFb_id();
            String profile_url = "http://graph.facebook.com/"+user_fb_id+"/picture?type=large&redirect=true&width=1000&height=1000";

            PostFacebookUserDetials.postUserDetials(Apis.userDetialPostURl, MainActivity.this);
            GetProfilePicture.userProfilePicture(getApplicationContext(), profile_url);


            Intent intent = new Intent(getApplicationContext() , HomeActivity.class);
            intent.putExtra("UserName" , userDetials.getFirstName()+" "+userDetials.getLastName());
            startActivity(intent);
            finish();

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

    private void handlingNullUserInfo(UserDetials userDetials){

        if(userDetials.getFb_id() == null){
            userDetials.setFb_id("010101010101");
        }if(userDetials.getFirstName() == null){
            userDetials.setFirstName("FirstName");
        } if(userDetials.getLastName() == null){
            userDetials.setLastName("LastName");
        } if(userDetials.getMobileNo() == null){
            userDetials.setMobileNo("0000000000");
        } if(userDetials.getEmail() == null){
            userDetials.setEmail("yourMail@gmail.com");
        } if (userDetials.getDob() == null){
            userDetials.setDob("0000-00-00");
        } if(userDetials.getGender() == null){
            userDetials.setGender("");
        } if (userDetials.getZone() == null){
            userDetials.setZone("");
        } if(userDetials.getDistrict() == null){
            userDetials.setDistrict("");
        } if(userDetials.getLocation() == null){
            userDetials.setLocation("");
        } if(userDetials.getProfilePicUrl() == null){
            userDetials.setProfilePicUrl("http://www.google.com");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        simpleFacebook.onActivityResult(requestCode, resultCode, data);
        MyUtils.showLog(data.toString());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        simpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(PostFacebookUserDetials.progressDialog != null){
            if(PostFacebookUserDetials.progressDialog.isShowing()){
                PostFacebookUserDetials.progressDialog.dismiss();
            }
        }
    }
}
