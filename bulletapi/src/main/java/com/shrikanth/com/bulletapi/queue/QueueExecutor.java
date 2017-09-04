package com.shrikanth.com.bulletapi.queue;

/**
 * Created by shrikanth on 9/3/17.
 */
public interface QueueExecutor {
    void enqueue(Task task);
    void remove(Task task);
}
