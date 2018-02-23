package com.GoldenMine.thread.threadAPI;

import com.GoldenMine.thread.threadAPI.unit.TimeUnitFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class APIMultiThread extends APIThread {
    private List<APISingleThread> threads = new ArrayList<APISingleThread>();

    public APIMultiThread(TimeUnitFactory factory, double unit, int time) {
        this(factory.convert(unit), time);
    }

    public APIMultiThread(double fps, int time) {

        long start = System.currentTimeMillis();

        //create threads
        for (int i = 0; i < time; i++) {
            APISingleThread t = new APISingleThread(start, fps, (int) Math.round(fps / time * 1000 * i)) {
                @Override
                public void onThreadExecute() throws InterruptedException {
                    onThreadExecute();
                }

                @Override
                public void onKeepUp() {
                    onKeepUp();
                }

                @Override
                public void onInterrupt() {
                    onInterrupt();
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onPause() {

                }

                @Override
                public void onResume() {

                }

                @Override
                public void onStop() {

                }
            };
            threads.add(t);
        }
    }

    @Override
    public void pause() {
        onPause();
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
        onResume();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).resume(start);
        }
    }

    @Override
    public void stop() {
        onStop();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).stop();
        }
    }

    public void start() {
        onStart();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
    }

}
