package com.GoldenMine.Thread.ThreadAPI;

import com.GoldenMine.Thread.ThreadAPI.Unit.TimeUnitFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class APIMultiThread implements APIThreadable {
    private List<APIThread> threads = new ArrayList<APIThread>();

    public APIMultiThread(TimeUnitFactory factory, double unit, int time) {
        this(factory.convert(unit), time);
    }

    public APIMultiThread(final double fps, final int time) {
        /*create threads*/
        for (int i = 0; i < time; i++) {

            APIThread t = new APIThread(fps, (int) Math.round(fps / time * 1000 * i)) {
                public void onThreadExecute() throws InterruptedException {
                    process();
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
            threads.add(t);
        }
    }

    @Override
    public void APIPause() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).APIPause();
        }
    }

    @Override
    public void APIResume() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).APIResume();
        }
    }

    @Override
    public void APIStop() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).APIStop();
        }
    }

    public void start() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
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

    private void processStop() {
        onStop();
    }

}
