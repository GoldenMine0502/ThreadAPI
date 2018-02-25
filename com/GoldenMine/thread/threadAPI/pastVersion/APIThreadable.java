package GoldenMine.thread.threadAPI.pastVersion;

public interface APIThreadable {
    void onThreadExecute() throws InterruptedException;
    void onKeepUp();
    void onInterrupt();
    void onStart();
    void onPause();
    void onResume();
    void onStop();

    void APIPause();
    void APIResume();
    void APIStop();
}
