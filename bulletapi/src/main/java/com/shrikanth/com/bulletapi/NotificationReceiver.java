package com.shrikanth.com.bulletapi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shrikanth on 8/20/17.
 */

public abstract class NotificationReceiver<T> {
    protected T listener;
    protected List<String> subscriptions;
    protected List<String> stickySubscriptions;

    public NotificationReceiver(T listener) {
        this.listener = listener;
        this.subscriptions = new ArrayList<>();
        this.stickySubscriptions = new ArrayList<>();
    }

    public abstract void handleNotification(String id, Object data);

    public List<String> getSubscriptions(){
        return subscriptions;
    }

    public List<String> getStickySubscriptions() {
        return stickySubscriptions;
    }
}
