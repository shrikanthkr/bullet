package com.shrikanth.com.access_objects;

import com.shrikanth.com.bulletapi.Subscribe;

import javax.lang.model.element.Element;

/**
 * Created by shrikanth on 8/20/17.
 */

public class AnnotatedMethod {

    Element element;
    String methodName;
    Subscribe annotation;
    //String[] params;


    public AnnotatedMethod(Element element) {
        this.element = element;
        this.methodName = element.getSimpleName().toString();
        annotation = element.getAnnotation(Subscribe.class);
    }


    public String getNotificationId() {
        return annotation.id();
    }

    public String getMethodName() {
        return methodName;
    }
}
