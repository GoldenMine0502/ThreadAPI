package GoldenMine.thread.threadAPI;

public class Delay {
    private long start;
    private long keepUp = -2000;
    private double fps = 0;
    private int ms;
    private double tick = 0;
    private int plusms = 0;

    public Delay(double fps, long start) {
        this.start = start;
        this.fps = fps;

        double time = 1000D/fps;
        int ms = (int) time;
        int ns = (int) ((time - ms)*1000000);
        this.ms = ms;

        double val = ns*fps;
        int interval = (int) (1000000-(val-(int)val/1000000*1000000));

        //System.out.println(ms + ", " + ns + ", " + interval);

        if(interval>=1000000) {
            interval-=1000000;
        }

        if(interval > 0)
            plusms = 1;

        if (interval < 0)
            plusms = -1;
    }

    public Delay(double fps) {
        this(fps, System.currentTimeMillis());
    }

    public int getMS() {
        return ms;
    }

    private int getUpdateMS() {
        tick++;
        if(tick>=fps) {
            tick -= fps;
            return plusms;
        }
        return 0;
    }

    public void setKeepUpTime(long keepUpTime) {
        keepUp = -Math.abs(keepUpTime);
    }

    public boolean autoCompute() throws InterruptedException {
        int ms = getRemainMS(start);
        int msplus = getUpdateMS();

        start+=getMS() + msplus;

        //System.out.println(getRemainMS(start));
        /* keep up calculation */
        long cal = 0;
        if(getRemainMS(start)<=keepUp) {
            while(getRemainMS(start + cal)<=0)
                cal+=getMS() + getUpdateMS();

            //System.out.println(cal);
        }

        start+=cal;
        /* sleep */
        Delay.sleep(ms + msplus);


        return cal!=0;
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

    public int getRemainMS(long start) {
        //System.out.println("remain: " + start);
        return (int) (getMS() - (System.currentTimeMillis() - start));
    }

    public void setTime(long time) {
        this.start = time;
    }

    public long getTime() {
        return start;
    }

    public static void sleep(int ms) throws InterruptedException {
        if(ms>0)
         Thread.sleep(ms);
    }
}