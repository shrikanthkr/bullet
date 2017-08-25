package com.shrikanth.com.bulletapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by shrikanth on 8/23/17.
 */

public class Subscriber {
    String id;
    Object subscriberInstance;
    NotificationReceiver generatedObject;
    private static final String SUFFIX = "$$NotificationReceiver";

    public Subscriber(String id, Object subscriberInstance) {
        this.id = id;
        this.subscriberInstance = subscriberInstance;
        String className = subscriberInstance.getClass().getCanonicalName() + SUFFIX;
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            Constructor obj = clazz.getConstructor(subscriberInstance.getClass());
            Object callerProxyInstance = obj.newInstance(subscriberInstance);
            generatedObject = (NotificationReceiver)callerProxyInstance;
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getSubscriberInstance() {
        return subscriberInstance;
    }

    public void setSubscriberInstance(Object subscriberInstance) {
        this.subscriberInstance = subscriberInstance;
    }

    public NotificationReceiver getGeneratedObject() {
        return generatedObject;
    }

    public void setGeneratedObject(NotificationReceiver generatedObject) {
        this.generatedObject = generatedObject;
    }

    public List getSubscriptionIDs(){
        return  this.generatedObject.getSubscriptions();
    }

    public void cleanup(){

    }

}
