package com.GoldenMine.thread.threadAPI;

public class Delay {
    private long start;
    private long keepUp = -2000;

    private double fps = 0;

    private int ms;

    private int tick = 0;
    private int interval = 0;

    //private long start;

    public Delay(double fps, long start) {
        double time = 1000D/fps;
        int ms = (int)time;
        int ns = (int) ((time - ms)*1000000);
        double val = ns*fps;

        this.start = start;

        this.fps = fps;
        this.ms = ms;
        this.interval = (int) (1000000-(val-(int)val/1000000*1000000));

        if(this.interval==1000000) {
            this.interval = 0;
        }
    }

    public Delay(double fps) {
        this(fps, System.currentTimeMillis());
    }

    public Delay(int fps, int delay) {
        this(fps);
        tick = delay;
    }

    public int getMS() {
        return ms;
    }

    public int update() {
        tick++;
        if(tick==fps) {
            tick = 0;
            return interval;
        }
        return 0;
    }

    public int getUpdateMS() {
        int updated = update();
        int msplus = 0;

        if(updated>0) {
            msplus=1;
        }
        if(updated<0) {
            msplus=-1;
        }

        return msplus;
    }

    public void setKeepUpTime(long keepUpTime) {
        keepUp = keepUpTime;
    }

    @Deprecated
    public long keepUp(long start, int keepup) {
        long cal = 0;
        if(getRemainMS(start)<=keepup) {
            while(getRemainMS(start + cal)>=-keepup)
                cal+=getMS() + getUpdateMS();
        }
        //System.out.println("cal: " + cal);
        return cal;
    }

    public boolean autoCompute() throws InterruptedException {
        int ms = getRemainMS(start);
        int msplus = getUpdateMS();

        start+=getMS() + msplus;

        Delay.sleep(ms + msplus);

        long cal = 0;
        if(getRemainMS(start)<=keepUp) {
            while(getRemainMS(start + cal)>=-keepUp)
                cal+=getMS() + getUpdateMS();
        }

        start+=cal;

        return cal!=0;
    }

    @Deprecated
    public long autoCompute(long start) throws InterruptedException {
        int ms = getRemainMS(start);
        int msplus = getUpdateMS();

        start+=getMS() + msplus;

        Delay.sleep(ms + msplus);

        return start;
    }

    public double getFPS() {
        return fps;
    }

    public static void sleep(int ms, int ns) throws InterruptedException {
        if (ms > 0) {
            if (ns > 0) {
                Thread.sleep(ms, ns);
            } else {
                Thread.sleep(ms);
            }
        } else {
            if (ns > 0) {
                Thread.sleep(0, ns);
            }
        }
    }

    public static void sleep(int ms) throws InterruptedException {
        if(ms>0)
         Thread.sleep(ms);
    }

    public int getRemainMS(long start) {
        //System.out.println("remain: " + start);
        return (int) (getMS() - (System.currentTimeMillis() - start));
    }

    public void setTime(long time) {
        this.start = time;
    }
}
