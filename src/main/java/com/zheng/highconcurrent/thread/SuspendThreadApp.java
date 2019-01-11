package com.zheng.highconcurrent.thread;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2019年01月11日			zhenglian			    Initial.
 *
 * </pre>
 */
public class SuspendThreadApp {
    
    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (SuspendThreadApp.class) {
                System.out.println(Thread.currentThread().getName() + " execute...");
                // suspend会挂起当前线程，但是这个挂起并不会释放它占有的资源，比如同步锁，所以其他线程将会阻塞等待
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        
        t1.start();
        t2.start();
    }
    
}
