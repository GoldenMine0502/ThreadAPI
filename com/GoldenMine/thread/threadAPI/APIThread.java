package GoldenMine.thread.threadAPI;

public abstract class APIThread {

    /**
     * it is defined when thread is started
     * 쓰레드가 시작했을때에 대해 정의합니다.
     */
    public abstract void start();


    /**
     * it is defined when thread is paused
     * 쓰레드가 일시 정지할 때에 대해 정의합니다.
     */
    public abstract void pause();


    /**
     * it is defined when thread is resumed
     * 쓰레드가 다시 시작했을 때에 대해 정의합니다.
     */
    public abstract void resume();


    /**
     * it is defined when thread is stopped
     * 쓰레드가 멈췄을 때에 대해 정의합니다.
     */
    public abstract void stop();

    /**
     * it is defined when thread is resumed, but it does not be used externally
     * 쓰레드가 다시 시작했을 때에 대해 정의합니다. 그러나 이 메서드는 내부적 처리를 위한 메서드입니다.
     */
    protected abstract void resume(long start);
}
