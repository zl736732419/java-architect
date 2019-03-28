package com.zheng.jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
public class ThreadWaitingApp {
    
    public static void createBusyThread() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    ;
            }
        }, "testBusyThread").start();
    }
    
    public static void createLockThread(final Object lock) {
        new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testLockThread").start();
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
        createBusyThread();
        reader.readLine();
        createLockThread(new Object());
    }
}
