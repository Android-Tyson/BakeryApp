package com.urbangirlbakeryandroidapp.alignstech;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.otto.Subscribe;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;
import com.urbangirlbakeryandroidapp.alignstech.activity.FillProfile;
import com.urbangirlbakeryandroidapp.alignstech.activity.NoticeBoard;
import com.urbangirlbakeryandroidapp.alignstech.activity.UserProfile;
import com.urbangirlbakeryandroidapp.alignstech.bus.PostComplainEvent;
import com.urbangirlbakeryandroidapp.alignstech.bus.PostFbUserDetailsEvent;
import com.urbangirlbakeryandroidapp.alignstech.controller.PostFacebookUserDetials;
import com.urbangirlbakeryandroidapp.alignstech.fragment_dialog.About_Us;
import com.urbangirlbakeryandroidapp.alignstech.fragment_dialog.MyComplains;
import com.urbangirlbakeryandroidapp.alignstech.fragments.CakesFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.GiftsFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.fragments.OfferFragment;
import com.urbangirlbakeryandroidapp.alignstech.gcm.QuickstartPreferences;
import com.urbangirlbakeryandroidapp.alignstech.gcm.RegistrationIntentService;
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
    private String fb_id, firstName, lastName, mobileNo, email,
            dob, gender, zone, district, location, profilePicUrl;
    public static MaterialDialog materialDialog;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;

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

        myGcmTask();
    }

    @Override
    public void init(Bundle bundle) {

        allowArrowAnimation();
        if (getIntent().getStringExtra("LearningPattern") != null &&
                getIntent().getStringExtra("LearningPattern").equals("true")) {
        } else {
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
            account = new MaterialAccount(getResources(), "You're not logged in.", "Click here to login with facebook."
                    , R.drawable.empty_image, R.drawable.drawer_bg);
        }

        addAccount(account);
        setAccountListener(this);

        addSection(newSection(getResources().getString(R.string.home), R.mipmap.home, new HomeFragment()));

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
//        addSection(newSection(getResources().getString(R.string.accessories), R.mipmap.accessories, new AccessoriesFragment()));
        addSection(newSection(getResources().getString(R.string.title_activity_notice_board), R.mipmap.notice_drawer, new Intent(this , NoticeBoard.class)));
        if (MyUtils.isUserLoggedIn(this)) {
            addSection(newSection(getResources().getString(R.string.complain), R.mipmap.complain_drawer, new MaterialSectionListener() {
                @Override
                public void onClick(MaterialSection materialSection) {
                    if (MyUtils.isNetworkConnected(getApplicationContext()))
                    {
                        new MyComplains().show(getSupportFragmentManager(), "welcome_screen_tag");
                    }
                }
            }));
        }
        addBottomSection(newSection(getResources().getString(R.string.about), R.mipmap.settings, new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection materialSection) {

                new About_Us().show(getSupportFragmentManager() , "ABOUT_US");

            }
        }));

    }


    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

        if (!MyUtils.isUserLoggedIn(this)) {
            if (MyUtils.isNetworkConnected(this)) {
                simpleFacebook.login(onLoginListener);
                MyUtils.showLog("");
            } else {
                MyUtils.showToast(this, "Please Check your Internet Connection And try again...");
            }
        } else {
            Intent intent = new Intent(this, UserProfile.class);
            startActivity(intent);
        }
    }


    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }


    @Override
    public void onUserPhotoLoaded(MaterialAccount account) {
        super.onUserPhotoLoaded(account);
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
            materialDialog = new MaterialDialog.Builder(MainActivity.this).
                    content("Loading Please wait...").cancelable(false).progress(true, 0).show();
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

            PostFacebookUserDetials.postUserDetials(Apis.userDetialPostURl, MainActivity.this);

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

        }
        if (userDetials.getLastName() == null) {

        }
        if (userDetials.getMobileNo() == null) {

        }
        if (userDetials.getEmail() == null) {

        }
        if (userDetials.getDob() == null) {

        }
        if (userDetials.getGender() == null) {

        }
        if (userDetials.getZone() == null) {

        }
        if (userDetials.getDistrict() == null) {

        }
        if (userDetials.getLocation() == null) {

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
    protected void onStop() {
        super.onStop();
        if (materialDialog != null) {
            if (materialDialog.isShowing()) {
                materialDialog.dismiss();
            }
        }
    }


    @Subscribe
    public void userDetailsPostResponse(PostFbUserDetailsEvent event) {

        MyUtils.saveDataInPreferences(getApplicationContext(), "USER_LOGGED_IN", "LOGGED_IN");
        MyUtils.saveDataInPreferences(getApplicationContext(), "USER_ID", fb_id);
        if (DataBase_Utils.isUserInfoDataExists())
            DataBase_Utils.deleteUserInfoList();
        DataBase_UserInfo dataBase_userInfo = new DataBase_UserInfo(fb_id, firstName, lastName, mobileNo, email, dob, gender, zone, district, location, profilePicUrl);
        dataBase_userInfo.save();
        MyUtils.showToast(getApplicationContext(), "You are Successfully Logged in. Please Fill All the info..");
        Intent intent = new Intent(getApplicationContext(), FillProfile.class);
        intent.putExtra("FacebookIntent", "FB_DATA");
        startActivity(intent);
        finish();

    }


    @Override
    protected void onDestroy() {
        MyBus.getInstance().unregister(this);
        super.onDestroy();

    }


    // GCM Job
    private void myGcmTask() {

            if (MyUtils.isNetworkConnected(MainActivity.this)) {

                mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                        boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                        if (sentToken) {


                        } else {


                        }
                    }
                };

                if (checkPlayServices()) {
                    // Start IntentService to register this application with GCM.
                    Intent intent = new Intent(this, RegistrationIntentService.class);
                    startService(intent);
                }

            }


    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Subscribe
    public void userPostResponse(PostComplainEvent event) {

        MyUtils.showToast(this, "Your complain is successfully posted..");

    }
}

