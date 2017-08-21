package com.shrikanth.com.notificationsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shrikanth.com.bulletapi.Subscribe;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Subscribe(id ="login")
    public void onLogin(){}

    @Subscribe(id ="sing")
    public Object onSing(String songId){
        return null;
    }

}
