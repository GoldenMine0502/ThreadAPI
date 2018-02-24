package com.GoldenMine.thread.threadAPI;

import com.GoldenMine.thread.threadAPI.unit.TimeUnitFactory;

public class APISingleThread extends APIThread {
    Thread thread;

    private boolean stop = false;
    private boolean paused = false;

    private int firstRemain;

    private Delay delay;

    private APIThreadHandler handler;

    public APISingleThread(TimeUnitFactory factory, double unit, APIThreadHandler handler) {
        this(System.currentTimeMillis(), factory, unit, 0, handler);
    }

    public APISingleThread(double fps, APIThreadHandler handler) {
        this(System.currentTimeMillis(), fps, handler);
    }

    /*for APIMultiThread */
    APISingleThread(long start, TimeUnitFactory factory, double unit, int firstRemain, APIThreadHandler handler) {
        this(start, factory.convert(unit), firstRemain, handler);
    }

    /*for APIMultiThread */
    APISingleThread(long start, double fps, int firstRemain, APIThreadHandler handler) {
        this(start, fps, handler);
        this.firstRemain = firstRemain;
    }


    private APISingleThread(long start, double fps, APIThreadHandler handler) {
        this.handler = handler;

        delay = new Delay(fps, start + firstRemain);

        delay.setKeepUpTime(-2000);

        thread = new Thread(() -> {
            sleepInMulti();

            while (!stop) {
                try {
                    if (paused) {
                        Thread.sleep(Integer.MAX_VALUE);
                        continue;
                    }

                    handler.onThreadExecute();

                    if (delay.autoCompute()) {
                        handler.onKeepUp();
                    }
                } catch (InterruptedException ex) {
                    handler.onInterrupt();
                    sleepInMulti();
                }
            }
        });
    }


    private void sleepInMulti() {
        try {
            if (firstRemain > 0)
                Thread.sleep(firstRemain);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start() {
        handler.onStart();
        thread.start();
    }

    @Override
    public void stop() {
        handler.onStop();
        stop = true;
        thread.interrupt();
    }

    @Override
    void resume(long start) {
        handler.onResume();
        delay.setTime(start + firstRemain);
        paused = false;
        thread.interrupt();
    }


    @Override
    public void pause() {
        handler.onPause();
        paused = true;
        thread.interrupt();
    }

    @Override
    public void resume() {
        resume(System.currentTimeMillis());
    }


}
