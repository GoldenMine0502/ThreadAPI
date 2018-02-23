package com.GoldenMine.thread.threadAPI;

import com.GoldenMine.thread.threadAPI.pastVersion.Delay;
import com.GoldenMine.thread.threadAPI.unit.TimeUnitFactory;

public abstract class APISingleThread extends APIThread {
    Thread thread;

    private double fps;
    private boolean stop = false;
    private boolean paused = false;

    private int firstRemain;

    private Delay delay;
    private long start;

    public APISingleThread(TimeUnitFactory factory, double unit) {
        this(System.currentTimeMillis(), factory, unit, 0);
    }

    public APISingleThread(double fps) {
        this(System.currentTimeMillis(), fps);
    }

    /*for APIMultiThread */
    protected APISingleThread(long start, TimeUnitFactory factory, double unit, int firstRemain) {
        this(start, factory.convert(unit), firstRemain);
    }

    /*for APIMultiThread */
    protected APISingleThread(long start, double fps, int firstRemain) {
        this(start, fps);
        this.firstRemain = firstRemain;
    }


    private APISingleThread(long start, double fps) {
        this.start = start;
        this.fps = fps;

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
                    onThreadExecute();

                    if (delay.autoCompute()) {
                        onKeepUp();
                    }
                } catch (InterruptedException ex) {
                    onInterrupt();
                    sleepInMulti();
                }
            }
        });
    }


    private void sleepInMulti() {
        try {
            if (start > 0)
                Thread.sleep(start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public double getFPS() {
        return fps;
    }

    @Override
    public void start() {
        onStart();
        thread.start();
    }

    public void stop() {
        onStop();
        stop = true;
        thread.interrupt();
    }

    @Override
    void resume(long start) {
        onResume();
        this.start = start;
        paused = false;
        thread.interrupt();
    }


    public void pause() {
        onPause();
        paused = true;
        thread.interrupt();
    }

    @Override
    public void resume() {
        resume(System.currentTimeMillis());
    }
}
