package com.zheng.highconcurrent.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

/**
 * @Author zhenglian
 * @Date 2019/2/24
 */
public class SynchronousQueueTest {
    
    private SynchronousQueue<Integer> queue;
    
    @Before
    public void init() {
        queue = new SynchronousQueue<>(true);
    }
    
    @Test
    public void test() throws Exception {
        System.out.println("start");
        new Thread(()-> {
            try {
                queue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println("1s");
        
        new Thread(()-> {
            try {
                queue.put(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println("1s");
        
        new Thread(()-> {
            try {
                queue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println("1s");
        
        new Thread(()-> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()-> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()-> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
    
}
