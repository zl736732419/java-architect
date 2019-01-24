package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author zhenglian
 * @Date 2019/1/23
 */
public class ReadWriteLockApp {
    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    public static void main(String[] args) throws Exception {
//        addReadLock();
        addWriteReadLock();
    }

    private static void addWriteReadLock() throws Exception {
        // 跟踪先写锁再读锁的情况
        new Thread(() -> {
            writeLock.lock();
            System.out.println("write lock for: " + Thread.currentThread().getName());
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            
            try {
                readLock.lock();
                
            } finally {
                writeLock.unlock();
            }
        }, "t1").start();
        
        Thread.sleep(500);
        
        new Thread(() -> {
            writeLock.lock();
            System.out.println("write lock for: " + Thread.currentThread().getName());
        }, "t2").start();
        
    }

    private static void addReadLock() throws Exception {
        //        System.out.println(1<<1); // =2，补0

        // 跟踪两个读锁的情况
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
