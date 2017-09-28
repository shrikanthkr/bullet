package com.shrikanth.com.bulletapi.queue;

/**
 * Created by shrikanth on 9/3/17.
 */
abstract class QueueExecutor {
    PendingQueue queue;

    QueueExecutor() {
        queue = new PendingQueue();
    }

    abstract public void enqueue(Task task);
    abstract public void remove(Task task);
}
