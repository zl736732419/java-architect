package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的part & unpark类似于线程的suspend & resume,但是前者更安全，不会出现后者遇到的死锁问题
 * 该类的操作是支持使用的，而线程的suspend & resume则是不支持使用的
 * park能响应中断，但是它并不会抛出异常
 * 可以通过调用当前线程的Thread.currentThread().isInterrupted()方法来判断
 * @Author zhenglian
 * @Date 2019/1/12
 */
public class LockSupportApp {
    public static Object lock = new Object();

    private static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    private static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("in " + getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        t1.start();
        Thread.sleep(1000);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
    }
}
