package com.shrikanth.com.bulletapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by shrikanth on 8/22/17.
 */

public class NotificationApi {

    private static final Object mLock = new Object();
    private static NotificationApi INSTANCE;
    private static Map<String, Subscriber> mSubscribersByID = new HashMap<>();
    private static Map<String, HashSet<String>> mSubscribersByNotificationId = new HashMap<>();


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
            addByNotificationIds(subscriber);
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
            removeFromNotificationsIds(receiverID);
        }
    }

    public static void notify(String id){
        notify(id, null);
    }

    public static void notify(String id, Object data){
        if( mSubscribersByNotificationId.containsKey(id)){
            HashSet<String> subscribers = mSubscribersByNotificationId.get(id);
            for (String subscriberId : subscribers) {
                Subscriber subscriber = mSubscribersByID.get(subscriberId);
                subscriber.notify(id, data);
            }
        }
    }



    private void addByNotificationIds(Subscriber subscriber){
        HashSet<String> subscriptions = subscriber.getSubscriptions();
        for(String id : subscriptions){
            HashSet<String> subscriberIds;
            if(mSubscribersByNotificationId.containsKey(id)){
                subscriberIds = mSubscribersByNotificationId.get(id);
            }else{
                subscriberIds = new HashSet<>();
                mSubscribersByNotificationId.put(id, subscriberIds);
            }
            subscriberIds.add(subscriber.getId());
        }
    }

    private void removeFromNotificationsIds(String receiverId){
        for (Map.Entry<String, HashSet<String>> entry : mSubscribersByNotificationId.entrySet()) {
            HashSet<String> subscriberIds = entry.getValue();
            subscriberIds.remove(receiverId);
        }
    }
}
