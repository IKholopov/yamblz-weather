package com.example.toor.yamblzweather.common;

/**
 * Created by igor on 7/29/17.
 */

public class SyncEntity {
    private final Object syncObject = new Object();

    public void waitFor() throws InterruptedException {
        synchronized (syncObject) {
            syncObject.wait();
        }
    }

    public void notifyOne() {
        synchronized (syncObject) {
            syncObject.notify();
        }
    }

    public void waitTimeout(long millis) throws InterruptedException {
        synchronized (syncObject) {
            syncObject.wait(millis);
        }
    }
}
