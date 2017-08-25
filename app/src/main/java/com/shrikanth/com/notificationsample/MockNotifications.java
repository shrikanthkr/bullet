package com.shrikanth.com.notificationsample;

import android.os.AsyncTask;

import com.shrikanth.com.bulletapi.NotificationApi;

/**
 * Created by shrikanth on 8/25/17.
 */

public class MockNotifications {

    public static void mock(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                NotificationApi.notify("login");
                return null;
            }

        }.execute();
    }

}
