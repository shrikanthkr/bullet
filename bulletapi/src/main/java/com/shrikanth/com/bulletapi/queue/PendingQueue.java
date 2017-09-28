package com.shrikanth.com.bulletapi.queue;

/**
 * Created by shrikanth on 9/3/17.
 */
class PendingQueue {
    private PendingTask head;
    private PendingTask tail;

    PendingQueue() {

    }

    synchronized void enqueue(Task task){
        PendingTask pendingTask = new PendingTask(task);
        if(tail != null){
            tail.next = pendingTask;
            tail = pendingTask;
        }else if(head == null){
            head = tail = pendingTask;
        }
        notify();
    }
    synchronized PendingTask poll(){
        PendingTask pendingTask = head;
        if(head != null){
            head = head.next;
            if(head == null){
                tail = null;
            }
        }
        return pendingTask;
    }


}
