package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.urbangirlbakeryandroidapp.alignstech.MainActivity;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.controller.NormalUserRegister;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditProfile extends AppCompatActivity {

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.user_full_name)
    EditText user_full_name;

    @InjectView(R.id.user_email)
    EditText user_email;

    @InjectView(R.id.user_mobile)
    EditText user_mobile;

    @InjectView(R.id.user_dob)
    EditText user_dob;

    @InjectView(R.id.user_gender)
    EditText user_gender;

    @InjectView(R.id.user_location)
    EditText user_location;

    @InjectView(R.id.user_zone)
    EditText user_zone;

    @InjectView(R.id.user_district)
    EditText user_district;

    private String fb_id, fullName, email, mobileNo, dob, gender, location, zone, district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_register);
        ButterKnife.inject(this);
        initializeToolbar();
//        MyBus.getInstance().register(this);
        setEditTextFields();
    }

    private void setEditTextFields() {

        if(Db_Utils.isUserInfoDataExists()) {

            List<DataBase_UserInfo> userInfos = Db_Utils.getUserInfoList();
            user_full_name.setText(userInfos.get(0).getFirstName()+" "+userInfos.get(0).getLastName());
            user_email.setText(userInfos.get(0).getEmail());
            user_mobile.setText(userInfos.get(0).getMobileNo());
            user_dob.setText(userInfos.get(0).getDob());
            user_gender.setText(userInfos.get(0).getGender());
            user_location.setText(userInfos.get(0).getLocation());
            user_zone.setText(userInfos.get(0).getZone());
            user_district.setText(userInfos.get(0).getDistrict());

        }
    }


    private void initializeToolbar() {

        toolbar.setTitle(R.string.edit_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getEditTextFields() {

        fb_id = user_email.getText().toString();
        fullName = user_full_name.getText().toString();
        email = user_email.getText().toString();
        mobileNo = user_mobile.getText().toString();
        dob = user_dob.getText().toString();
        gender = user_gender.getText().toString();
        location = user_location.getText().toString();
        zone = user_zone.getText().toString();
        district = user_district.getText().toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_normal_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {

            getEditTextFields();
            List<String> userInfo = new ArrayList<>();
            userInfo.add(fb_id);
            userInfo.add(fullName);
            userInfo.add(mobileNo);
            userInfo.add(email);
            userInfo.add(dob);
            userInfo.add(gender);
            userInfo.add(zone);
            userInfo.add(district);
            userInfo.add(location);

            if (MyUtils.isNetworkConnected(this)) {
                if (checkIfAnyFieldsAreEmpty()) {
                    if (MyUtils.isEmailValid(email, this) && MyUtils.isValidPhoneNumber(mobileNo, this)) {
                        NormalUserRegister.postUserDetials(Apis.userDetialPostURl, this, userInfo);
                    }
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkIfAnyFieldsAreEmpty() {
        if (fullName.isEmpty() || email.isEmpty() || mobileNo.isEmpty() || dob.isEmpty()
                || gender.isEmpty() || location.isEmpty() || zone.isEmpty() || district.isEmpty()) {
            MyUtils.showToast(this, "Some of the Field are empty..");
            return false;
        }
        return true;
    }

//    @Subscribe
//    public void isSuccess(NormalRegisterEventBus eventBus) {
//        if (!eventBus.getResponse().isEmpty()) {
//
////            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container_normal_register, Welcome_Screen.newInstance()).commit();
//
//            new Welcome_Screen().show(getSupportFragmentManager(), "welcome_screen_tag");
//
//        }
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(this , MainActivity.class));
            finish();

        } else if (keyCode == KeyEvent.KEYCODE_HOME) {

            startActivity(new Intent(this, MainActivity.class));
            finish();


        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        MyBus.getInstance().unregister(this);
//    }


}
