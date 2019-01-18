package com.zheng.highconcurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.IntUnaryOperator;

/**
 * @Author zhenglian
 * @Date 2019/1/17
 */
public class AtomicIntegerArrayApp {
    
    @Test
    public void init() {
        AtomicIntegerArray array = new AtomicIntegerArray(5);
        System.out.println(array.get(0));
    }
    
    @Test
    public void updater() {
        AtomicIntegerArray array = new AtomicIntegerArray(5);
        IntUnaryOperator operator = new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                return operand + 1;
            }
        };
        operator = operator.andThen(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                return operand * 2;
            }
        });
        int result = array.updateAndGet(0, operator);
        System.out.println(result);
    }
    
}
