package com.GoldenMine.thread.threadAPI;

public abstract class APIThread {

    public abstract void start();
    public abstract void pause();
    public abstract void resume();
    abstract void resume(long start);
    public abstract void stop();

    public abstract void onThreadExecute() throws InterruptedException;
    public abstract void onKeepUp();
    public abstract void onInterrupt();
    public abstract void onStart();
    public abstract void onPause();
    public abstract void onResume();
    public abstract void onStop();
}
