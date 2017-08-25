package com.shrikanth.com.notificationsample;

import android.os.Bundle;

import com.shrikanth.com.bulletapi.Subscribe;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Subscribe(id ="login")
    public void onLogin(){}

}
