package com.shrikanth.com.bulletapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shrikanth on 8/20/17.
 * 
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Subscribe {
    String id();
}
