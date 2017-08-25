package com.shrikanth.com.bulletapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by shrikanth on 8/22/17.
 */

public class NotificationApi {

    private static final Object mLock = new Object();
    private static NotificationApi INSTANCE;
    private Map<String, Subscriber> mSubscribersByID = new HashMap<>();
    private Map<String, List<Subscriber>> mSubscribersByNotificationId = new HashMap<>();
    private Map<String, HashSet<String>> stickyEvents = new HashMap<>();

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
            Subscriber subscriber = new Subscriber(id, receiver);
            mSubscribersByID.put(id, subscriber);
        }
    }

    public void unRegister(String receiverID){
        synchronized (mLock) {
            Subscriber subscriber = mSubscribersByID.get(receiverID);
            subscriber.cleanup();
            mSubscribersByID.remove(receiverID);

        }
    }

    public void completeDestroy(String receiverID){
        synchronized (mLock) {
            unRegister(receiverID);
            removeFromSticky(receiverID);
        }
    }

    private void removeFromSticky(String receiverID){

    }
}
