package com.shrikanth.com.bulletapi.queue;

/**
 * Created by shrikanth on 9/3/17.
 */
public class PendingQueue {
    PendingTask head;
    PendingTask tail;

    public PendingQueue() {
    }

    public synchronized void enqueue(Task task){
        PendingTask pendingTask = new PendingTask(task);
        if(tail != null){
            tail.next = pendingTask;
            tail = pendingTask;
        }else if(head == null){
            head = tail = pendingTask;
        }
        notify();
    }
    public synchronized PendingTask poll() throws InterruptedException{
        PendingTask pendingTask = head;
        if(head != null){
            head = head.next;
            if(head == null){
                tail = null;
            }
        }else{
            wait();
        }
        return pendingTask;
    }


}
