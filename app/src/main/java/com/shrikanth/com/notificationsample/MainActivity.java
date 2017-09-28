package com.shrikanth.com.notificationsample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shrikanth.com.bulletapi.NotificationApi;
import com.shrikanth.com.bulletapi.Subscribe;
import com.shrikanth.com.bulletapi.ThreadMode;
import com.shrikanth.com.notificationsample.models.Constants;
import com.shrikanth.com.notificationsample.models.User;

public class MainActivity extends BaseActivity {

    User s = new User();
    Button main, post, async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = (Button)findViewById(R.id.main_thread);
        post = (Button)findViewById(R.id.post);
        async = (Button)findViewById(R.id.async);
        main.setOnClickListener(mainClickListener);
        async.setOnClickListener(asyncClick);
        post.setOnClickListener(postClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void callUserCheck(User u){}

    @Subscribe(id ="login", threadMode = ThreadMode.MAIN)
    public void onLogin(final String message){
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Subscribe(id ="sing")
    public void onSing(String songId){
        Toast.makeText(MainActivity.this, songId, Toast.LENGTH_LONG).show();
    }

    @Subscribe(id = Constants.X, sticky = true)
    public void onSing(User complex){

    }

    @Subscribe(id ="Onprimitive", threadMode = ThreadMode.ASYNC)
    public void onPrimitive(int prim){
        Log.d("ASYNC", prim+"");
    }


    private View.OnClickListener mainClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NotificationApi.notify("login", "Posting on Main Thread");
        }
    };

    private View.OnClickListener asyncClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i=0;i<10;i++) {
                NotificationApi.notify("Onprimitive", 5);
            }
        }
    };

    private View.OnClickListener postClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                NotificationApi.notify("sing", "songId");
        }
    };

}
