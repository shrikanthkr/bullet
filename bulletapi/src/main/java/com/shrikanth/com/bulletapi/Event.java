package com.shrikanth.com.bulletapi;

/**
 * Created by shrikanth on 9/18/17.
 */

public class Event {
    private String id;
    private ThreadMode threadMode;
    private boolean sticky;

    public Event(String id, ThreadMode threadMode, boolean sticky) {
        this.id = id;
        this.threadMode = threadMode;
        this.sticky = sticky;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }
}
