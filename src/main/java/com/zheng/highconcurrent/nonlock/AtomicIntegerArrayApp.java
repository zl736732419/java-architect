package com.zheng.highconcurrent.nonlock;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 数组原子类，保证对数组的每个元素操作都是原子性的
 * @Author zhenglian
 * @Date 2019/1/10
 */
public class AtomicIntegerArrayApp {
    
    private static AtomicIntegerArray array = new AtomicIntegerArray(10);
    
    public static void main(String[] args) throws Exception {
        
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    array.incrementAndGet(j % 10);
                }
            });
            ts[i].start();
        }
        
        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }
        
        System.out.println(array);
    }
    
}
