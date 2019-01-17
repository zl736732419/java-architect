package com.zheng.highconcurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

public class AtomicBooleanApp {

    @Test
    public void testBoolean() {
        AtomicBoolean ab = new AtomicBoolean(true);
        System.out.println(ab.get());
    }

    /**
     * 对当前结果做一元运算
     * 但是他们之间可以通过andThen继续组装
     */
    @Test
    public void testIntegerUpdater() {
        AtomicInteger num = new AtomicInteger(2);
        IntUnaryOperator operator = (operand) -> operand * operand;
        IntUnaryOperator intUnaryOperator = operator.andThen((operand) -> operand * operand);
        int result = num.updateAndGet(intUnaryOperator);
        System.out.println(result);
    }

    @Test
    public void testIntegerAccumulater() {
        AtomicInteger num = new AtomicInteger(2);
        int result = num.accumulateAndGet(3, new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        });
        
        System.out.println(result);
    }
    
}
