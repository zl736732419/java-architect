package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock & condition.await() / condition.signal() 
 * 与 synchronized & Object.wait() / Object.notify()相似
 * @Author zhenglian
 * @Date 2019/1/12
 */
public class ConditionApp {
    
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    
    private static Runnable r = () -> {
        lock.lock();
        try {
            condition.await();
            System.out.println(Thread.currentThread().getId() + " thread keep going");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    };
    
    public static void main(String[] args) {
        Thread t = new Thread(r);
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        condition.signal();
        lock.unlock();
    }
    
}
