package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author zhenglian
 * @Date 2019/1/24
 */
public class CyclicBarrierApp {
    private static CyclicBarrier barrier = new CyclicBarrier(2, () -> {
        System.out.println("finish all thread task at this point.");
    });
    
    public static void main(String[] args) {
        new Thread(() -> handleTask(), "t1").start();
        new Thread(() -> handleTask(), "t2").start();
    }
    
    private static void handleTask() {
        try {
            Thread.sleep((new Random().nextInt(5) + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread " + Thread.currentThread().getName() + " finished");
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
