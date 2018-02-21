package com.GoldenMine.Thread.ThreadAPI;

import com.GoldenMine.Thread.ThreadAPI.Unit.TimeUnitFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class APIMultiThread implements APIThreadable {
    private final long start = System.currentTimeMillis();

    private double fps;

    private int time;
    private List<APIThread> threads = new ArrayList<APIThread>();

    public APIMultiThread(TimeUnitFactory factory, double unit, int time) {
        this(factory.convert(unit), time);
    }

    public APIMultiThread(final double fps, final int time) {
        Delay delay = new Delay(fps*time);

        this.fps = fps;
        this.time = time;

        int keepup = -1000;

        new Thread() {
            public void run() {
                try {
                    long starts = start;
                    //long starts = start;

                    int lastremain = 0;

                    for (int i = 0; i < time; i++) {

                        APIThread t = new APIThread(fps, lastremain) {
                            public void onThreadExecute() throws InterruptedException {
                                process(); // APIMultiThread의 execute()메서드를 실행하고 싶다
                            }

                            @Override
                            public void onKeepUp() {
                                processKeepup();
                            }

                            @Override
                            public void onInterrupt() {
                                processIntterrupt();
                            }

                            @Override
                            public void onStart() {
                                processStart();
                            }

                            @Override
                            public void onPause() {
processPause();
                            }

                            @Override
                            public void onResume() {
                                processResume();
                            }

                            @Override
                            public void onStop() {
                                processStop();
                            }
                        };
                        t.start();
                        threads.add(t);

                        int ms = delay.getRemainMS(starts);
                        int msplus = delay.getUpdateMS();
                        long cal = delay.keepUp(starts, keepup);

                        if(cal>0) {
                            onKeepUp();
                            starts+=cal;
                            continue;
                        }
                        starts+=delay.getMS() + msplus;

                        Delay.sleep(ms + msplus);

                        lastremain = ms + msplus;
                    }
                } catch(InterruptedException ex) {
                    //-onInterrupt();
                }
            }
        }.start();
    }
    @Override
    public void APIPause() {
        for(int i = 0; i < threads.size(); i++) {
            threads.get(i).APIPause();
        }
    }

    @Override
    public void APIResume() {
        for(int i = 0; i < threads.size(); i++) {
            threads.get(i).APIResume();
        }
    }

    @Override
    public void APIStop() {
        for(int i = 0; i < threads.size(); i++) {
            threads.get(i).APIStop();
        }
    }

    private void process() throws InterruptedException {
        onThreadExecute();
    }

    private void processKeepup() {
        onKeepUp();
    }

    private void processIntterrupt() {
        onInterrupt();
    }

    private void processStart() {
        onStart();
    }

    private void processPause() {
        onPause();
    }

    private void processResume() {
        onResume();
    }

    private void processStop() { onStop(); }

}
