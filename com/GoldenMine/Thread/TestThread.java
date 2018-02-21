package com.GoldenMine.Thread;

import com.GoldenMine.Thread.ThreadAPI.APIMultiThread;
import com.GoldenMine.Thread.ThreadAPI.APIThread;
import com.GoldenMine.Thread.ThreadAPI.Unit.TimeUnitFactory;

public class TestThread {
    public static void main(String[] args) {
        //new APIThread(����, ����);
        //new APIMultiThread(����, ����, �������);


        APIThread t = new APIThread(TimeUnitFactory.SECOND, 1) {
            long first = -1;

            int time;

            @Override
            public void onThreadExecute() throws InterruptedException {
                    Thread.sleep(10);
                    if (first == -1) {
                        first = System.currentTimeMillis();
                    }
                    //System.out.println(System.currentTimeMillis() - first);

                    System.out.println(++time + "�ʰ� �������ϴ�");
            }

            @Override
            public void onKeepUp() {

            }

            @Override
            public void onInterrupt() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onStop() {

            }
        };
        t.start(); // ������ ����

        try {
            Thread.sleep(5000L);
            t.APIPause(); // 5���� ������ �Ͻ�����
            Thread.sleep(3000L);
            t.APIResume(); // 8���� ������ �簳
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println(System.currentTimeMillis() + ", " + System.nanoTime() + ", " + (System.nanoTime()-System.currentTimeMillis()*1000000));
        //System.out.println(System.currentTimeMillis() + ", " + System.nanoTime());
        /*Scanner scan = new Scanner(System.in);

        System.out.print("input FPS: ");
        int fps = scan.nextInt();

        APIMultiThread mt = new APIMultiThread(fps, 4) {
            int i = 0;
            @Override
            public void onThreadExecute() {
                System.out.println("running: " + i++);
            }

            @Override
            public void onKeepUp() {

            }

            @Override
            public void onInterrupt() {

            }
        };*/
    }
}
