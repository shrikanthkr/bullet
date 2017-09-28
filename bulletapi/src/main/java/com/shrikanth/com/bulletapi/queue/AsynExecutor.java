package com.shrikanth.com.bulletapi.queue;

import java.util.concurrent.ExecutorService;


/**
 * Created by shrikanth on 9/8/17.
 */

public class AsynExecutor extends QueueExecutor implements Runnable {

    ExecutorService executorService;
    public AsynExecutor(ExecutorService executorService) {
        super();
        this.executorService = executorService;
    }

    @Override
    public void enqueue(Task task) {
        synchronized (this) {
            queue.enqueue(task);
            executorService.execute(this);
        }
    }

    @Override
    public void remove(Task task) {
        synchronized (this) {

        }
    }


    @Override
    public void run() {
        PendingTask task = queue.poll();
        if(task != null)
            task.execute();
    }
}
