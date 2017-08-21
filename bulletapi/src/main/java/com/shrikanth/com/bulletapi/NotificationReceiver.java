package com.shrikanth.com.bulletapi;

/**
 * Created by shrikanth on 8/20/17.
 */

public abstract class NotificationReceiver<T> {
    protected T listener;

    public NotificationReceiver(T listener) {
        this.listener = listener;
    }

    public abstract void handleNotification(String id, Object data);


}
