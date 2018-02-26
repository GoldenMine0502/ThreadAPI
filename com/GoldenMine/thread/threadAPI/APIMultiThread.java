package GoldenMine.thread.threadAPI;

import com.GoldenMine.thread.threadAPI.unit.TimeUnit;

import java.util.ArrayList;
import java.util.List;

public final class APIMultiThread extends APIThread {
    private List<APISingleThread> threads = new ArrayList<APISingleThread>();

    private APIThreadHandler handler;

    public APIMultiThread(TimeUnit factory, double unit, int time, APIThreadHandler handler) {
        this(factory.convert(unit), time, handler);
    }

    public APIMultiThread(double fps, int time, APIThreadHandler handler) {

        this.handler = handler;

        long start = System.currentTimeMillis();

        //create threads
        for (int i = 0; i < time; i++) {
            APISingleThread t = new APISingleThread(start, fps, (int) Math.round(fps / time * 1000 * i), new APIThreadHandler() {
                @Override
                public void onThreadExecute() throws InterruptedException {
                    handler.onThreadExecute();
                }

                @Override
                public void onKeepUp() {
                    handler.onKeepUp();
                }

                @Override
                public void onInterrupt() {
                    handler.onInterrupt();
                }
            });
            threads.add(t);
        }
    }

    @Override
    public void pause() {
        handler.onPause();
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).pause();
        }
    }

    @Override
    public void resume() {
        resume(System.currentTimeMillis());
    }

    @Override
    protected void resume(long start) {
        handler.onResume();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).resume(start);
        }
    }

    @Override
    public void stop() {
        handler.onStop();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).stop();
        }
    }

    @Override
    public void start() {
        handler.onStart();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
    }

}
