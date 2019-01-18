package com.zheng.highconcurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * @Author zhenglian
 * @Date 2019/1/18
 */
public class AtomicLongArrayApp {
    @Test
    public void init() {
        AtomicLongArray array = new AtomicLongArray(5);
        System.out.println(array.get(0));
    }
}
