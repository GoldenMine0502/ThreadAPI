package com.GoldenMine.Thread.ThreadAPI;

import com.GoldenMine.Thread.ThreadAPI.Unit.TimeUnitFactory;

public abstract class APIThread extends Thread implements APIThreadable {
    private double fps;

    private boolean stop = false;
    private boolean paused = false;

    private long start;

    private Delay delay;
    final int keepup = -2000;

    private int firstremain;

    public APIThread(TimeUnitFactory factory, double unit) {
        this(factory, unit, 0);
    }

    protected APIThread(TimeUnitFactory factory, double unit, int firstremain) {
        this(factory.convert(unit), firstremain);
    }

    public APIThread(double fps) {
        this.fps = fps;
    }

    protected APIThread(double fps, int firstremain) {
        this(fps);
        this.firstremain = firstremain;
    }

    @Override
    public void run() {
        onStart();

        delay = new Delay(fps);
        start = System.currentTimeMillis();

        //System.out.println(start);

        while(!stop) {
            try {
                if(paused) {
                    Thread.sleep(Integer.MAX_VALUE);
                    interrupt();
                    //start += delay.keepUp(start, delay.getRemainMS(start), keepup);
                }
                onThreadExecute();

                start = delay.autoCompute(start);
                long cal = delay.keepUp(start, keepup);

                if(cal>0) {
                    onKeepUp();
                    start+=cal;
                    continue;
                }
            } catch(InterruptedException ex) {
                onInterrupt();
                //System.out.println("start: " + start);
                try {
                    if(firstremain>0)
                        Thread.sleep(firstremain);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("interrupted");
            }
        }
    }

    public void APIStop() {
        onStop();
        stop = true;
        interrupt();
    }

    public void APIResume() {
        onResume();
        start = System.currentTimeMillis() + firstremain;
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

/*
Delay ����

333333 + 333333 + 333334

3 1000/3 = 333.333333333 = 1000000-1

2/3 666666 + 666666 + 666666 = 2000000-2

7 857142 = 6000000-6

11 909090 = 9999990-10

13 923076 = 11999988-12

17 823529 = 13999993-7

19 631578 = 631578-18

23 478260 = 10999980 - 20

29 482758 = 13999982 - 18

12378 80788 =

�׻� xx,000,000�� ���;� ��

���� ��%1000000 = 0�� ��� ����

int val = ns * 1000000 * ���� ��

��%1000000 = 0�ΰ�� ���� �� �����̹Ƿ�
int val2 = 1000000-(val-val2/1000000*1000000);
// ��%1000000 = 0�� �ּڰ�

�׸��� ���� ����ŭ ����Ŭ�� �� ������
�ش� ���� ����


��Ƽ�����忡���� �����带 ������ ����
���������� ����

int �������;

�������/a

�ִ��� �����ϰ� �����尣 ������ �ٿ��� ��
���� Delay ���� ����
�׸��� �̷��� ���� Delay���� ������ ���� ����




 */
