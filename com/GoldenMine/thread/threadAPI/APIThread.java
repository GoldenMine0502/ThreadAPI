package GoldenMine.thread.threadAPI;

public abstract class APIThread {

    public abstract void start();
    public abstract void pause();
    public abstract void resume();
    public abstract void stop();

    protected abstract void resume(long start);
}
