package com.zheng.jvm;

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
 *  2019年03月22日			zhenglian			    Initial.
 *
 * </pre>
 */
public class DeadLockApp {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
    
    private static void createThread(String threadName, Object lock1, Object lock2) {
        new Thread(()->{
            synchronized (lock1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " can start working.");
                }
            }
        }, threadName).start();
    }
    
    public static void main(String[] args) {
        createThread("thread01", lock1, lock2);
        createThread("thread02", lock2, lock1);
    }
}
