package com.shrikanth.com.bulletapi;

import android.os.Looper;

import com.shrikanth.com.bulletapi.queue.HandlerExecutor;
import com.shrikanth.com.bulletapi.queue.Task;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shrikanth on 8/23/17.
 */

class Subscriber {
    private String id;
    private NotificationReceiver notificationReceiver;
    private static final String SUFFIX = "$$NotificationReceiver";
    private static final HandlerExecutor handlerExecutor = new HandlerExecutor(Looper.getMainLooper());
    private enum STATE{
        INIT, ALIVE, PAUSED, DESTROYED
    }
    private STATE currentState;
    private Map<String, Object> pendingEvents;

    Subscriber(String id, Object subscriberInstance) {
        this.currentState = STATE.INIT;
        this.id = id;
        this.pendingEvents = new HashMap<>();
        String className = subscriberInstance.getClass().getCanonicalName() + SUFFIX;
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            Constructor obj = clazz.getConstructor(subscriberInstance.getClass());
            Object callerProxyInstance = obj.newInstance(subscriberInstance);
            notificationReceiver = (NotificationReceiver)callerProxyInstance;
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        this.currentState = STATE.ALIVE;
    }

    Map<String, Event> getSubscriptions(){
        return notificationReceiver.getSubscriptions();
    }

    String getId() {
        return id;
    }

    void notify(String id, Object data){
        postToReceiver(id, data);
    }

    void bindSubscriber(Object subscriberInstance) {
        notificationReceiver.setListener(subscriberInstance);
        currentState = STATE.ALIVE;
    }

    void sendPendingNotifications(){
        for (Map.Entry<String, Object> entry : pendingEvents.entrySet()) {
            notify(entry.getKey(), entry.getValue());
        }
    }
    void unregister(){
        this.currentState = STATE.PAUSED;
    }

    void destroy(){
        this.currentState = STATE.DESTROYED;
        notificationReceiver.removeListener();
    }

    private void postToReceiver(final String id, final Object data){

        handlerExecutor.enqueue(new Task() {
            @Override
            public void execute() {
                if(currentState == STATE.ALIVE) {
                    notificationReceiver.handleNotification(id, data);
                }else{
                    if(notificationReceiver.isSticky(id)) {
                        pendingEvents.put(id, data);
                    }
                }
            }
        });
    }

}
