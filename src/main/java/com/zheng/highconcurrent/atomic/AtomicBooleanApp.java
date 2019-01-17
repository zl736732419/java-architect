package com.zheng.highconcurrent.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanApp {
    public static void main(String[] args) {
        AtomicBoolean ab = new AtomicBoolean(true);
        System.out.println(ab.get());
    }
}
