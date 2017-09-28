package com.shrikanth.com.bulletapi.queue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;



/**
 * Created by shrikanth on 9/8/17.
 */

public class HandlerExecutor extends QueueExecutor {

    private Handler handler;
    private boolean isHandlerRunning = false;
    public HandlerExecutor(Looper looper) {
        super();
        handler = new Handler(looper, handlerCallback);
    }

    @Override
    public void enqueue(Task task) {
        synchronized (this) {
            queue.enqueue(task);
            if (!isHandlerRunning) {
                isHandlerRunning = true;
                handler.sendMessage(handler.obtainMessage());
            }
        }
    }

    @Override
    public void remove(Task task) {
        synchronized (this) {

        }
    }
    private Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            synchronized (this){
                while (true) {
                    PendingTask pendingTask = queue.poll();
                    if(pendingTask == null){
                        isHandlerRunning = false;
                        return false;
                    }
                    pendingTask.execute();
                }
            }
        }
    };


}
