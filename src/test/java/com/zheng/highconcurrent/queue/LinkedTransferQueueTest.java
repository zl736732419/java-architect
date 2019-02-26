package com.zheng.highconcurrent.queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @Author zhenglian
 * @Date 2019/2/24
 */
public class LinkedTransferQueueTest {
    
    private LinkedTransferQueue<Integer> queue;
    
    @Before
    public void before() {
        queue = new LinkedTransferQueue<>();
    }
    
    @Test
    public void add() throws Exception {
        queue.add(1);
        queue.take();
        queue.add(2);
    }
    
    @After
    public void print(){ 
        System.out.println(queue);
    }
}
