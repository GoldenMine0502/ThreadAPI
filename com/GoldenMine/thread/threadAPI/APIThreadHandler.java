package GoldenMine.thread.threadAPI;

public interface APIThreadHandler {

    /**
     * one tick is passed
     * 쓰레드 틱이 한번 돌았을때 실행
     */
    default void onThreadExecute() throws InterruptedException { }

    /**
     * if your code on onThreadExecute() is too very heavy to calculate time normally,
     * The ThreadAPI create new time calculation and refresh.
     * then, this method is called.
     * onThreadExecute() 코드가 매우 무거워 정상적으로 일정한 주기를 계산하기 어려울 때, ThreadAPI는 새로운 시간 계산을 만들어 새로고침합니다.
     * 그때 이 메서드가 실행됩니다.
     */
    default void onKeepUp() { }

    /**
     * when interrupt() is triggered after onPause, onResume, onStop
     * onPause, onResume, onStop 이후 interrupt()가 실행될때.
     */
    default void onInterrupt() { }

    /**
     * when thread is started
     * 쓰레드가 시작될 때
     */
    default void onStart() { }

    /**
     * when thread is paused
     * 쓰레드가 일시적으로 멈출 때
     */
    default void onPause() { }

    /**
     * when thread is resumed
     * 쓰레드가 재개될 때
     */
    default void onResume() { }

    /**
     * when thread is stopped
     * 쓰레드가 멈출 때
     */
    default void onStop() { }
}
