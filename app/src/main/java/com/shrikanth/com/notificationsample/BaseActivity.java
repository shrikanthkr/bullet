package com.shrikanth.com.notificationsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shrikanth.com.bulletapi.NotificationApi;

import java.util.UUID;

/**
 * Created by shrikanth on 8/23/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected String id = UUID.randomUUID().toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            id = savedInstanceState.getString("UNIQUE_ID");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        NotificationApi.getInstance().register(this, id);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationApi.getInstance().unRegister(id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationApi.getInstance().destroy(id);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("UNIQUE_ID", id);
    }
}
