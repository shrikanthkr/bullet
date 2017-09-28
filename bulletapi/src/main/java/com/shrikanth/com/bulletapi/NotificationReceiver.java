package com.shrikanth.com.bulletapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shrikanth on 8/20/17.
 */

public abstract class NotificationReceiver<T> {
    protected T listener;
    protected Map<String, Event> subscriptions;

    public NotificationReceiver(T listener) {
        this.listener = listener;
        subscriptions = new HashMap<>();
    }

    void setListener(T listener) {
        this.listener = listener;
    }

    void removeListener(){
        listener = null;
    }

    public abstract void handleNotification(String id, Object data);

    Map<String, Event> getSubscriptions(){
        return subscriptions;
    }


    boolean isSticky(String id){
        return subscriptions.containsKey(id) && subscriptions.get(id).isSticky();
    }
}
