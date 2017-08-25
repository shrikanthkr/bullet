package com.shrikanth.com.notificationsample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.shrikanth.com.bulletapi.Subscribe;
import com.shrikanth.com.notificationsample.models.User;

public class MainActivity extends BaseActivity {

    User s = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SecondActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        MockNotifications.mock();
    }

    private void callUserCheck(User u){}

    @Subscribe(id ="login")
    public void onLogin(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Subscribe(id ="login")
    public void somthingElse(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Subscribe(id ="sing")
    public Object onSing(String songId){
        return null;
    }

    @Subscribe(id ="complexObject", sticky = true)
    public Object onSing(User complex){
        return null;
    }

}
