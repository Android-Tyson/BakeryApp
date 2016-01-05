package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.controller.NormalUserRegister;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NormalRegister extends AppCompatActivity {

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


    }

    private void initializeToolbar() {

        toolbar.setTitle(R.string.user_info);
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
                    if (isEmailValid(email)) {

                        NormalUserRegister.postUserDetials(Apis.userDetialPostURl, this, userInfo);

                    }
                }
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Check if any of the editText Field is empty
    private boolean checkIfAnyFieldsAreEmpty() {
        if (fullName.isEmpty() || email.isEmpty() || mobileNo.isEmpty() || dob.isEmpty()
                || gender.isEmpty() || location.isEmpty() || zone.isEmpty() || district.isEmpty()) {
            MyUtils.showToast(this, "Some of the Field are empty..");
            return false;
        }
        return true;
    }


    // Check wether the entered mail is valid or not
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        } else {
            MyUtils.showToast(this, "Please enter a valid mail..");
            return false;
        }
    }

}
