package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可中断死锁检查
 *
 * @Author zhenglian
 * @Date 2019/1/12
 */
public class InterruptReentrantLockApp {

    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    private static class ReenterLockInt implements Runnable {
        private int lock;

        public ReenterLockInt(int lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                if (lock == 1) {
                    lock1.lockInterruptibly();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    lock2.lockInterruptibly();
                } else {
                    lock2.lockInterruptibly();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    lock1.lockInterruptibly();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
                System.out.println(Thread.currentThread().getId() + "线程退出");
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        ReenterLockInt r1 = new ReenterLockInt(1);
        ReenterLockInt r2 = new ReenterLockInt(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(2000);
        // 中断其中一个线程
        DeadThreadChecker.check();
    }

}
