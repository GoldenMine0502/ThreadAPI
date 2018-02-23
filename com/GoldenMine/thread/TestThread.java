package com.GoldenMine.thread;

import com.GoldenMine.thread.threadAPI.APISingleThread;
import com.GoldenMine.thread.threadAPI.handler.APIThreadHandler;

public class TestThread {
    public static void main(String[] args) {
        //new APISingleThread(단위, 숫자);
        //new APIMultiThread(단위, 숫자, 쓰레드수);


        APISingleThread thread = new APISingleThread(new APIThreadHandler() {
            @Override
            public void onThreadExecute() throws InterruptedException {
                System.out.println("Hello, World!");
            }

            @Override
            public void onKeepUp() {
                System.out.println("onKeepUp()");
            }

            @Override
            public void onInterrupt() {
                System.out.println("onInterrupt()");
            }

            @Override
            public void onStart() {
                System.out.println("onStart()");
            }

            @Override
            public void onPause() {
                System.out.println("onPause()");
            }

            @Override
            public void onResume() {
                System.out.println("onResume()");
            }

            @Override
            public void onStop() {
                System.out.println("onStop()");
            }
        }, 1);

        thread.start();

        try {
            Thread.sleep(5000L);
            thread.pause();

            Thread.sleep(5000L);
            thread.resume();

            Thread.sleep(10000L);

            System.exit(0);

        } catch(Exception ex) {
            ex.printStackTrace();
        }


        /*
        APIMultiThread t = new APIMultiThread(1, 3) {
            int time;

            @Override
            public void onThreadExecute() throws InterruptedException {
                    System.out.println(++time + " Sec");
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
        t.start(); // 쓰레드 시작

        try {
            thread.sleep(5000L);
            t.APIPause(); // 5초후 쓰레드 일시정지
            thread.sleep(3000L);
            t.APIResume(); // 8초후 쓰레드 재개
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
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
