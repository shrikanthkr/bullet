package com.shrikanth.com.bulletapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shrikanth on 8/22/17.
 */

public class NotificationApi {

    private static final Object mLock = new Object();
    private static NotificationApi INSTANCE;
    private static Map<String, Subscriber> mSubscribersByID = new HashMap<>();
    private static Map<String, List<Subscriber>> mSubscribersByNotificationId = new HashMap<>();


    /*
    Avoid exposing the constructor for initialization
    */
    private void NotificationApi(){

    }

    public static NotificationApi getInstance(){
        synchronized (mLock){
            if(INSTANCE == null){
                INSTANCE = new NotificationApi();
            }
            return INSTANCE;
        }
    }

    public void register(Object receiver, String id){
        synchronized (mLock){
            Subscriber subscriber;
            if(mSubscribersByID.containsKey(id)){
                subscriber = mSubscribersByID.get(id);
                subscriber.bindSubscriber(receiver);
                subscriber.sendPendingNotifications();
            }else {
                subscriber = new Subscriber(id, receiver);
            }
            mSubscribersByID.put(id, subscriber);
        }
    }

    public void unRegister(String receiverID){
        synchronized (mLock) {
            Subscriber subscriber = mSubscribersByID.get(receiverID);
            subscriber.unregister();
        }
    }

    public void destroy(String receiverID){
        synchronized (mLock) {
            Subscriber subscriber = mSubscribersByID.get(receiverID);
            unRegister(receiverID);
            subscriber.destroy();
            mSubscribersByID.remove(receiverID);
        }
    }

    public static void notify(String id){
        notify(id, null);
    }

    public static void notify(String id, Object data){
        List<Subscriber> subscribers = mSubscribersByNotificationId.get(id);
        for (Subscriber subscriber : subscribers) {
           subscriber.notify(id, data);
        }
    }



    private void addToSubscriptions(String receiverID){

    }
}
