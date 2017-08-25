package com.shrikanth.com.notificationsample;

import android.os.AsyncTask;
import android.os.Handler;

import com.shrikanth.com.bulletapi.NotificationApi;
import com.shrikanth.com.notificationsample.models.User;

/**
 * Created by shrikanth on 8/25/17.
 */

public class MockNotifications {

    public static void mock(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                NotificationApi.notify("login", "This is message from other world");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NotificationApi.notify("complexObject", new User());
                    }
                }, 2000);
            }
        }.execute();
    }

}
