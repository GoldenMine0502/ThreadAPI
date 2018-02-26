package GoldenMine.thread.threadAPI;

import com.GoldenMine.thread.threadAPI.unit.TimeUnit;

import java.util.ArrayList;
import java.util.List;

public final class APIMultiThread extends APIThread {
    private List<APISingleThread> threads = new ArrayList<APISingleThread>();

    private APIThreadHandler handler;

    /**
     * if you want to use another unit, you can use instead of APIThread(double, APIThreadHandler)
     * FPS 단위가 아닌 특정한 단위를 사용하고 싶을 때, 이 생성자를 호출할 수 있습니다.
     * @param factory it is unit. 단위입니다.
     * @param unit it is number for unit 단위에 대한 수입니다.
     * @param handler you can define their events about triggered start, pause or etc. 특정한 상황에 대한 이벤트를 정의해줄 수 있습니다.
     */
    public APIMultiThread(TimeUnit factory, double unit, int time, APIThreadHandler handler) {
        this(factory.convert(unit), time, handler);
    }

    /**
     * you can use thread cycle using fps unit.
     * 쓰레드 실행 주기를 FPS 단위로 실행할 수 있습니다.
     * @param fps fps, 프레임
     * @param handler you can define their events about triggered start, pause or etc. 특정한 상황에 대한 이벤트를 정의해줄 수 있습니다.
     */
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


    /**
     * pause this thread
     * 쓰레드를 일시적으로 정지합니다.
     */
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

    /**
     * resume this thread
     * 쓰레드를 재개합니다.
     */
    @Override
    protected void resume(long start) {
        handler.onResume();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).resume(start);
        }
    }

    /**
     * stop this thread
     * 쓰레드를 정지합니다.
     */
    @Override
    public void stop() {
        handler.onStop();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).stop();
        }
    }

    /**
     * start this thread
     * 쓰레드를 시작합니다.
     */
    @Override
    public void start() {
        handler.onStart();

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
    }

}
