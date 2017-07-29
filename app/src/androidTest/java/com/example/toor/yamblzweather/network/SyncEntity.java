package com.example.toor.yamblzweather.network;

/**
 * Created by igor on 7/29/17.
 */

class SyncEntity {
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
}
