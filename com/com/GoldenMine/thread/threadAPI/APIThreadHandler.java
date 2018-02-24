package com.GoldenMine.thread.threadAPI;

public interface APIThreadHandler {
    default void onThreadExecute() throws InterruptedException { }
    default void onKeepUp() { }
    default void onInterrupt() { }
    default void onStart() { }
    default void onPause() { }
    default void onResume() { }
    default void onStop() { }
}
