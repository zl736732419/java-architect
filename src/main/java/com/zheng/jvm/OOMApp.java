package com.zheng.jvm;

import java.util.Vector;

/**
 * @Author zhenglian
 * @Date 2019/3/9
 */
public class OOMApp {
    
    public static void main(String[] args) {
        // -Xmx4m -Xms4m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/oom.dump 
        Vector vector = new Vector();
        for (int i = 0; i < 5; i++) { // 5m
            vector.add(new byte[1 * 1024 * 1024]);
        }
    }
}
