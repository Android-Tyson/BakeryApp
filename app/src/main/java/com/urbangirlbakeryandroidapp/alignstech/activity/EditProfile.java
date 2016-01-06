package com.urbangirlbakeryandroidapp.alignstech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.model.DataBase_UserInfo;
import com.urbangirlbakeryandroidapp.alignstech.utils.Db_Utils;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.List;

public class EditProfile extends AppCompatActivity {

//    @InjectView(R.id.imageView_profile_picture)
//    public NetworkImageView userProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//        ButterKnife.inject(this);
//        setProfilePicture();

    }

    private void setProfilePicture() {

        if(!Db_Utils.getUserInfoList().isEmpty()) {
            List<DataBase_UserInfo> list = Db_Utils.getUserInfoList();
            String profilePicUrl = list.get(0).getProfilePicUrl();
//            userProfilePicture.setImageUrl(profilePicUrl , MySingleton.getInstance(this).getImageLoader());
        }else {
            MyUtils.showLog("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
}
