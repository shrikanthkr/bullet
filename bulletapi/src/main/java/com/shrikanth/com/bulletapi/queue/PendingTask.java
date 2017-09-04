package com.shrikanth.com.bulletapi.queue;

/**
 * Created by shrikanth on 9/3/17.
 */
public class PendingTask {

    protected Task task;
    protected PendingTask next;
    protected enum STATE{
        PENDING, STARTED, KILLED
    }
    private STATE currentState;

    public PendingTask(Task task) {
        this.task = task;
        this.currentState = STATE.PENDING;
    }

    public void execute(){
        if(this.currentState != STATE.KILLED) {
            this.currentState = STATE.STARTED;
            task.execute();
        }
    }
    public void kill(){
        this.currentState = STATE.KILLED;
    }
}
