package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author zhenglian
 * @Date 2019/1/23
 */
public class ReadWriteLockApp {
    public static void main(String[] args) throws Exception {
//        System.out.println(1<<1); // =2，补0
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
        
        new Thread(() -> {
            readLock.lock();
            System.out.println("read lock for: " + Thread.currentThread().getName());
//            try {
//                System.out.println("read lock for: " + Thread.currentThread().getName());
//            } finally {
//                readLock.unlock();
//            }
            
        }, "t1").start();

        Thread.sleep(1000);
        
        new Thread(() -> {
            readLock.lock();
            System.out.println("read lock for: " + Thread.currentThread().getName());
//            try {
//                System.out.println("read lock for: " + Thread.currentThread().getName());
//            } finally {
//                readLock.unlock();
//            }

        }, "t2").start();
        
    }
}
