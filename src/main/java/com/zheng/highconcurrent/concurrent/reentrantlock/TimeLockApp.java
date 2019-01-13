package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zhenglian
 * @Date 2019/1/12
 */
public class TimeLockApp {
    
    private static final ReentrantLock lock = new ReentrantLock();
    
    private static Runnable timeLock = () -> {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                Thread.sleep(6000);
            } else {
                System.out.print(Thread.currentThread().getId() + " get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    };
    
    public static void main(String[] args) {
        Thread t1 = new Thread(timeLock);
        Thread t2 = new Thread(timeLock);
        t1.start();
        t2.start();
    }
}
