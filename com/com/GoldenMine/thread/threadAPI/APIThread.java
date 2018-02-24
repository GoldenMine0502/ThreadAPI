package com.GoldenMine.thread.threadAPI;

public abstract class APIThread {

    public abstract void start();
    public abstract void pause();
    public abstract void resume();
    abstract void resume(long start);
    public abstract void stop();
}
