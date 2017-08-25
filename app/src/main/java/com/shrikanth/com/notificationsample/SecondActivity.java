package com.shrikanth.com.notificationsample;

import android.os.Bundle;

import com.shrikanth.com.bulletapi.NotificationApi;
import com.shrikanth.com.bulletapi.Subscribe;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    @Subscribe(id ="login")
    public void onLogin(){}

}
