package com.zheng.highconcurrent.java8;

import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder内部也是采用与AtomicLong相同的cas操作，而不是使用锁做同步
 * 但是区别在于，LongAdder内部参考了ConcurrentHashMap的分段思想，将并行访问分离
 * 内部持有一个Cell[]，不同的线程并行访问的时候会对其中某一个Cell做操作，当发生并行冲突的时候，
 * Cell[]会被扩充，从而从原来的AtomicLong将多线程并行访问一个值转化成并行访问多个值，
 * 这样降低了线程并行产生冲突的概率。从而提高了并行访问效率，降低了冲突发生的概率
 * @Author zhenglian
 * @Date 2019/1/15
 */
public class LongAdderApp {
    private final static LongAdder num = new LongAdder();
    
    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        num.increment();
                    }
                }
            });
        }
        
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        
        System.out.println("result: " + num.sum());
    }
    
}
