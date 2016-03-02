package com.urbangirlbakeryandroidapp.alignstech;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.otto.Subscribe;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;
import com.urbangirlbakeryandroidapp.alignstech.activity.EditProfile;
import com.urbangirlbakeryandroidapp.alignstech.activity.Settings;
import com.urbangirlbakeryandroidapp.alignstech.activity.UserProfile;
import com.urbangirlbakeryandroidapp.alignstech.bus.PostFbUserDetailsEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostFacebookUserDetials;
import com.urbangirlbakeryandroidapp.alignstech.fragments.AccessoriesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.CakesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.model.UserDetials;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.DataBase_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener {

    public static MaterialAccount account;
    private SimpleFacebook simpleFacebook;
    public static UserDetials userDetials;
    private String fb_id , firstName ,lastName , mobileNo , email ,
            dob , gender , zone , district , location , profilePicUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyBus.getInstance().register(this);
        simpleFacebook = SimpleFacebook.getInstance(this);


        Permission[] permissions = new Permission[]{
                Permission.USER_PHOTOS,
                Permission.USER_HOMETOWN,
                Permission.USER_ABOUT_ME,
                Permission.USER_LOCATION,
                Permission.PUBLIC_PROFILE,
                Permission.EMAIL,
        };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getResources().getString(R.string.app_id))
                .setNamespace("BakeryApp")
                .setPermissions(permissions)
                .build();

        simpleFacebook.setConfiguration(configuration);

    }

    @Override
    public void init(Bundle bundle) {

        allowArrowAnimation();
        addMultiPaneSupport();
        if (getIntent().getStringExtra("LearningPattern") != null &&
                getIntent().getStringExtra("LearningPattern").equals("true")) {
        }else {
            disableLearningPattern();
        }
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
//        setDrawerHeaderImage(R.drawable.drawer_bg); // Out of memory BUG here

        addSection(newSection(getResources().getString(R.string.home), R.mipmap.home, HomeFragment.newInstance(0)));

        if (MyUtils.isUserLoggedIn(this)) {
            addSection(newSection(getResources().getString(R.string.profile), R.mipmap.profile, new Intent(this, UserProfile.class)));
        } else {
            addSection(newSection(getResources().getString(R.string.login), R.mipmap.login, new MaterialSectionListener() {
                @Override
                public void onClick(MaterialSection materialSection) {
                    if (MyUtils.isNetworkConnected(getApplicationContext())) {
                        simpleFacebook.login(onLoginListener);
                        MyUtils.showLog("");
                    } else {
                        MyUtils.showToast(getApplicationContext(), "Please Check your Internet Connection And try again...");
                    }
                }
            }));
        }

        addSection(newSection(getResources().getString(R.string.cakes), R.mipmap.cakes, new CakesFragment()));
        addSection(newSection(getResources().getString(R.string.gifts), R.mipmap.gifts, new GiftsFragment()));
        addSection(newSection(getResources().getString(R.string.offers), R.mipmap.offers, new OfferFragment()));
        addSection(newSection(getResources().getString(R.string.accessories), R.mipmap.accessories, new AccessoriesFragment()));

        addBottomSection(newSection(getResources().getString(R.string.settings), R.mipmap.settings, new Intent(this, Settings.class)));

    }


    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

//        if(!MyUtils.isUserLoggedIn(this)){
//            Bundle loginBundle = new Bundle();
//            loginBundle.putBoolean("isDirectLogin", true);
//            Intent loginIntent = new Intent(this, Login.class);
//            loginIntent.putExtras(loginBundle);
//            startActivity(loginIntent);
////            finish();
//        }

        if (!MyUtils.isUserLoggedIn(this)) {
            if (MyUtils.isNetworkConnected(this)) {
                simpleFacebook.login(onLoginListener);
                MyUtils.showLog("");
            } else {
                MyUtils.showToast(this, "Please Check your Internet Connection And try again...");
            }
        } else {
            MyUtils.showToast(this, "You are already logged in..");
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

            simpleFacebook.getProfile(properties, onProfileListener);

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


    OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile profile) {

//            MyUtils.saveDataInPreferences(getApplicationContext(), "USER_LOGGED_IN", "LOGGED_IN");

            userDetials = new UserDetials();

            userDetials.setFb_id(profile.getId());
            MyUtils.showLog("My profile id =" + profile.getId());
            userDetials.setFirstName(profile.getFirstName());
            MyUtils.showLog(" " + profile.getFirstName());
            userDetials.setLastName(profile.getLastName());
            MyUtils.showLog(" " + profile.getLastName());
            userDetials.setMobileNo(profile.getReligion());
            MyUtils.showLog(" " + profile.getReligion());
            userDetials.setEmail(profile.getEmail());
            MyUtils.showLog(" " + profile.getEmail());
            userDetials.setDob(profile.getBirthday());
            MyUtils.showLog(" " + profile.getBirthday());
            userDetials.setGender(profile.getGender());
            MyUtils.showLog(" " + profile.getGender());
            userDetials.setZone(profile.getLocale());
            MyUtils.showLog(" " + profile.getLocale());
            userDetials.setDistrict(profile.getLocale());
            MyUtils.showLog(" " + profile.getLocale());
            userDetials.setLocation(profile.getLocale());
            MyUtils.showLog(" " + profile.getLocale());
            userDetials.setProfilePicUrl(profile.getPicture());

            handlingNullUserInfo(userDetials);

            fb_id = userDetials.getFb_id();
            firstName = userDetials.getFirstName();
            lastName = userDetials.getLastName();
            mobileNo = userDetials.getMobileNo();
            email = userDetials.getEmail();
            dob = userDetials.getDob();
            gender = userDetials.getGender();
            zone = userDetials.getZone();
            district = userDetials.getDistrict();
            location = userDetials.getLocation();
            profilePicUrl = userDetials.getProfilePicUrl();

//            DataBase_UserInfo dataBase_userInfo = new DataBase_UserInfo(fb_id, firstName, lastName, mobileNo, email, dob, gender, zone, district, location, profilePicUrl);
//            dataBase_userInfo.save();

//            String user_fb_id = userDetials.getFb_id();
//            String profile_url = "http://graph.facebook.com/"+user_fb_id+"/picture?type=large&redirect=true&width=1000&height=1000";

            PostFacebookUserDetials.postUserDetials(Apis.userDetialPostURl, MainActivity.this);
//            GetProfilePicture.userProfilePicture(getApplicationContext(), profile_url);

//            MyUtils.saveDataInPreferences(getApplicationContext(), "USER_ID", fb_id);
//            Intent intent = new Intent(getApplicationContext(), EditProfile.class);
////            intent.putExtra("UserName" , userDetials.getFirstName()+" "+userDetials.getLastName());
//            intent.putExtra("FacebookIntent", "FB_DATA");
//            startActivity(intent);
//            finish();

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


    private void handlingNullUserInfo(UserDetials userDetials) {

        if (userDetials.getFb_id() == null) {
            userDetials.setFb_id("yourmail@gmail.com");
        }
        if (userDetials.getFirstName() == null) {
//            userDetials.setFirstName("Full");
        }
        if (userDetials.getLastName() == null) {
//            userDetials.setLastName("Name");
        }
        if (userDetials.getMobileNo() == null) {

        }
        if (userDetials.getEmail() == null) {
//            userDetials.setEmail("yourmail@gmail.com");
        }
        if (userDetials.getDob() == null) {
//            userDetials.setDob("");
        }
        if (userDetials.getGender() == null) {
//            userDetials.setGender("");
        }
        if (userDetials.getZone() == null) {
//            userDetials.setZone("");
        }
        if (userDetials.getDistrict() == null) {
//            userDetials.setDistrict("");
        }
        if (userDetials.getLocation() == null) {
//            userDetials.setLocation("");
        }
        if (userDetials.getProfilePicUrl() == null) {
            userDetials.setProfilePicUrl(Apis.defaultImageUrl);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        simpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (PostFacebookUserDetials.materialDialog != null) {
            if (PostFacebookUserDetials.materialDialog.isShowing()) {
                PostFacebookUserDetials.materialDialog.dismiss();
            }
        }
    }

    @Subscribe
    public void userDetailsPostResponse(PostFbUserDetailsEvent event){

        MyUtils.saveDataInPreferences(getApplicationContext(), "USER_LOGGED_IN", "LOGGED_IN");
        MyUtils.saveDataInPreferences(getApplicationContext(), "USER_ID", fb_id);
        if(DataBase_Utils.isUserInfoDataExists())
            DataBase_Utils.deleteUserInfoList();
        DataBase_UserInfo dataBase_userInfo = new DataBase_UserInfo(fb_id, firstName, lastName, mobileNo, email, dob, gender, zone, district, location, profilePicUrl);
        dataBase_userInfo.save();
        MyUtils.showToast(getApplicationContext(), "You are Successfully Logged in. Please Fill All the info..");
        Intent intent = new Intent(getApplicationContext(), EditProfile.class);
        intent.putExtra("FacebookIntent", "FB_DATA");
        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(MyBus.isRegister()){
//            MyBus.getInstance().unregister(this);
//        }
    }
}

