package com.example.toor.yamblzweather.common;

import io.reactivex.functions.Action;

/**
 * Created by igor on 7/29/17.
 */

public class SyncEntity {
    private final Object syncObject = new Object();
    private boolean correct = true;

    public boolean waitFor() throws InterruptedException {
        synchronized (syncObject) {
            syncObject.wait();
        }
        return correct;
    }

    public void notifyOne() {
        synchronized (syncObject) {
            syncObject.notify();
        }
    }

    public void onFail() {
            correct = false;
    }

    public void notifyOne(long delay) throws InterruptedException {
        synchronized (syncObject) {
            syncObject.wait(delay);
            syncObject.notify();
        }
    }

    public void runAssertion(Action assertion) throws Exception {
        try {
            assertion.run();
        } catch (AssertionError error) {
            this.onFail();
            throw error;
        } finally {
            this.notifyOne();
        }
    }

    public boolean waitTimeout(long millis) throws InterruptedException {
        synchronized (syncObject) {
            syncObject.wait(millis);
        }
        return correct;
    }
}
