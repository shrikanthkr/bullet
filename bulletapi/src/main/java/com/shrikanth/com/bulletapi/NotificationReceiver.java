package com.shrikanth.com.bulletapi;

import java.util.HashSet;

/**
 * Created by shrikanth on 8/20/17.
 */

public abstract class NotificationReceiver<T> {
    protected T listener;
    protected HashSet<String> subscriptions;
    protected HashSet<String> stickySubscriptions;

    public NotificationReceiver(T listener) {
        this.listener = listener;
        this.subscriptions = new HashSet<>();
        this.stickySubscriptions = new HashSet<>();
    }

    public abstract void handleNotification(String id, Object data);

    public HashSet<String> getSubscriptions(){
        return subscriptions;
    }

    public HashSet<String> getStickySubscriptions() {
        return stickySubscriptions;
    }

    public boolean isSticky(String id){
        return  stickySubscriptions.contains(id);
    }
}
