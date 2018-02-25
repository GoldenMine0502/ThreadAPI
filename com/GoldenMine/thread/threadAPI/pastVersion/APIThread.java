package GoldenMine.thread.threadAPI.pastVersion;

import com.GoldenMine.thread.threadAPI.pastVersion.Unit.TimeUnitFactory;

public abstract class APIThread extends Thread implements APIThreadable {
    private double fps;
    private boolean stop = false;
    private boolean paused = false;

    private int firstRemain;

    private Delay delay;
    private long start;


    public APIThread(TimeUnitFactory factory, double unit) {
        this(factory, unit, 0);
    }

    /*for APIMultiThread */
    protected APIThread(TimeUnitFactory factory, double unit, int firstremain) {
        this(factory.convert(unit), firstremain);
    }

    public APIThread(double fps) {
        this.fps = fps;
    }

    /*for APIMultiThread */
    protected APIThread(double fps, int firstremain) {
        this(fps);
        this.firstRemain = firstremain;
    }

    public void setCurrentTime(long start) {
        this.start = start;
    }

    @Override
    public void run() {
        onStart();

        delay = new Delay(fps, start + firstRemain);

        delay.setKeepUpTime(-2000);

        sleepInMulti();

        while(!stop) {
            try {
                if(paused) {
                    Thread.sleep(Integer.MAX_VALUE);
                    continue;
                }
                onThreadExecute();

                if(delay.autoCompute()) {
                    onKeepUp();
                }
            } catch(InterruptedException ex) {
                onInterrupt();
                sleepInMulti();
            }
        }
    }

    private void sleepInMulti() {
        try {
            if(start>0)
                Thread.sleep(start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void APIStop() {
        onStop();
        stop = true;
        interrupt();
    }

    public void APIResume() {
        onResume();
        start = System.currentTimeMillis() + start;
        paused = false;
        interrupt();
    }

    public void APIPause() {
        onPause();
        paused = true;
        interrupt();
    }

    /*
    @Override
            public void onTimerTick() {
                System.out.println("test");
            }
            @Override
            public void onTimerExpire() {
            }
            @Override
            public void onTimerInterrupt() {
            }
            @Override
            public void onTimerStart() {
            }
            @Override
            public void onTimerPause() {
            }
            @Override
            public void onTimerResume() {
            }
            public enum WatchType {
    FPS,SECOND,MILLISECOND,MINUTE,HOUR;
}
     */
}