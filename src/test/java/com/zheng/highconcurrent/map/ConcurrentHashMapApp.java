package com.zheng.highconcurrent.map;

import org.junit.Test;

public class ConcurrentHashMapApp {
    
    @Test
    public void testResizeStamp() {
        int n = 16;
        int result = Integer.numberOfLeadingZeros(n) | (1 << (16 - 1));
        System.out.println(result);
    }
    
}
