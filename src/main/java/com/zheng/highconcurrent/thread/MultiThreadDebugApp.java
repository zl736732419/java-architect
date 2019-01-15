package com.zheng.highconcurrent.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhenglian
 * @Date 2019/1/15
 */
public class MultiThreadDebugApp {
    
    private static List<Object> list = new ArrayList<>();
    
    private static class Handler implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                list.add(new Object());
            }
            System.out.println(Thread.currentThread().getId() + " thread finish");
        }
    }
    
    public static void main(String[] args) {
        Handler handler = new Handler();
        Thread t1 = new Thread(handler, "t1");
        Thread t2 = new Thread(handler, "t2");
        t1.start();
        t2.start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "t3").start();
    }
    
}
