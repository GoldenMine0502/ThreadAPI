package GoldenMine.thread.threadAPI;

import com.GoldenMine.thread.threadAPI.unit.TimeUnit;

public class APISingleThread extends APIThread {
    Thread thread;

    private boolean stop = false;
    private boolean paused = false;

    private int firstRemain;

    private Delay delay;

    private APIThreadHandler handler;


    /**
     * you can use thread cycle using fps unit.
     * 쓰레드 실행 주기를 FPS 단위로 실행할 수 있습니다.
     * @param fps fps, 프레임
     * @param handler you can define their events about triggered start, pause or etc. 특정한 상황에 대한 이벤트를 정의해줄 수 있습니다.
     */
    public APISingleThread(double fps, APIThreadHandler handler) {
        this(System.currentTimeMillis(), fps, handler);
    }

    /**
     * if you want to use another unit, you can use instead of APIThread(double, APIThreadHandler)
     * FPS 단위가 아닌 특정한 단위를 사용하고 싶을 때, 이 생성자를 호출할 수 있습니다.
     * @param factory it is unit. 단위입니다.
     * @param unit it is number for unit 단위에 대한 수입니다.
     * @param handler you can define their events about triggered start, pause or etc. 특정한 상황에 대한 이벤트를 정의해줄 수 있습니다.
     */
    public APISingleThread(TimeUnit factory, double unit, APIThreadHandler handler) {
        this(System.currentTimeMillis(), factory, unit, 0, handler);
    }

    /**
     * it is used to synchronize times in APIMultiThread
     * APIMultiThread에서 시간을 동기화하기 위해 사용합니다.
     * @param start start time, 시작 시간
     * @param factory unit, 단위
     * @param unit number for unit, 단위에 대한 수
     * @param firstRemain additional waiting time in onResume/onStart, onResume/onStart때에 추가로 기다려야 할 시간
     * @param handler you can define their events about triggered start, pause or etc. 특정한 상황에 대한 이벤트를 정의해줄 수 있습니다.
     */
    /*for APIMultiThread */
    APISingleThread(long start, TimeUnit factory, double unit, int firstRemain, APIThreadHandler handler) {
        this(start, factory.convert(unit), firstRemain, handler);
    }

    /**
     * same.
     * 같습니다.
     * @param start start time, 시작 시간
     * @param fps fps, 프레임
     * @param firstRemain additional waiting time in onResume/onStart, onResume/onStart때에 추가로 기다려야 할 시간
     * @param handler you can define triggered events. 특정한 상황에 대한 이벤트를 정의해줄 수 있습니다.
     */
    /*for APIMultiThread */
    APISingleThread(long start, double fps, int firstRemain, APIThreadHandler handler) {
        this(start, fps, handler);
        this.firstRemain = firstRemain;
    }

    private APISingleThread(long start, double fps, APIThreadHandler handler) {
        this.handler = handler;

        delay = new Delay(fps, start + firstRemain);

        delay.setKeepUpTime(-2000);

        thread = new Thread() {
            public void run() {
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
            }
        };
    }


    private void sleepInMulti() {
        try {
            if (firstRemain > 0)
                Thread.sleep(firstRemain);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * start this thread
     * 쓰레드를 시작합니다.
     */
    @Override
    public void start() {
        handler.onStart();
        thread.start();
    }


    /**
     * stop this thread
     * 쓰레드를 멈춥니다.
     */
    @Override
    public void stop() {
        handler.onStop();
        stop = true;
        thread.interrupt();
    }


    /**
     * resume this thread
     * 쓰레드를 멈춥니다.
     */
    @Override
    public void resume() {
        resume(System.currentTimeMillis());
    }


    /**
     * for APIMultiThread
     * APIMultiThread를 위한 것.
     */
    protected void resume(long start) {
        handler.onResume();
        delay.setTime(start + firstRemain);
        paused = false;
        thread.interrupt();
    }



    /**
     * pause this thread
     * 쓰레드를 일시적으로 멈춥니다.
     */
    @Override
    public void pause() {
        handler.onPause();
        paused = true;
        thread.interrupt();
    }


}
