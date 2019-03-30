package com.zheng.jvm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2019年03月15日			zhenglian			    Initial.
 *
 * </pre>
 */
public class JVMArgTest {

    /**
     * -verbose:gc
     * [GC (System.gc())  22002K->1336K(125952K), 0.0992883 secs]
     * [Full GC (System.gc())  1336K->1261K(125952K), 0.0245222 secs]
     */
    @Test
    public void verboseGC() throws Exception {
        gcCode();
    }

    /**
     * -XX:+PrintGC
     * [GC (System.gc())  21988K->1411K(125952K), 0.0018571 secs]
     * [Full GC (System.gc())  1411K->1262K(125952K), 0.0063178 secs]
     *
     * @throws Exception
     */
    @Test
    public void printGC() throws Exception {
        gcCode();
    }

    /**
     * -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
     * 0.794: [GC (System.gc()) [PSYoungGen: 20661K->1336K(38400K)] 20661K->1336K(125952K), 0.0954791 secs]
     * [Times: user=0.05 sys=0.00, real=0.10 secs]
     * 0.889: [Full GC (System.gc()) [PSYoungGen: 1336K->0K(38400K)]
     * [ParOldGen: 0K->1263K(87552K)] 1336K->1263K(125952K),
     * [Metaspace: 4809K->4809K(1056768K)], 0.0101103 secs]
     * [Times: user=0.02 sys=0.00, real=0.01 secs]
     *
     * @throws Exception
     */
    @Test
    public void printGCDetails() {
        gcCode();
    }

    /**
     * -XX:+PrintGCDetails -Xloggc:log/gc.log
     */
    @Test
    public void gcLogFile() {
        gcCode();
    }

    /**
     * gc前后打印堆信息
     * -XX:+PrintGCDetails -XX:+PrintHeapAtGC
     * {Heap before GC invocations=1 (full 0):
     * ...
     * [GC (System.gc()) [
     * ...
     * Heap after GC invocations=1 (full 0):
     */
    @Test
    public void printHeap() {
        gcCode();
    }

    /**
     * 打印程序启动过程中的类加载信息
     * -XX:+TraceClassLoading
     */
    @Test
    public void traceClassLoading() {
        gcCode();
    }

    /**
     * 打印程序启动类实例情况,需要手动按下ctrl+Break调出打印信息
     * -XX:+PrintClassHistogram
     */
    @Test
    public void printClassHistogram() throws Exception {
        gcCode();
        Thread.sleep(100000);
    }

    /**
     * -Xmx: 最大堆内存
     * -Xms: 最小堆内存
     * -Xmn: 新生代内存
     * -Xmx10m -Xms10m -Xmn6m -XX:+PrintGCDetails
     * PSYoungGen      total 5632K
     * ParOldGen       total 4096K
     */
    @Test
    public void heapSetting() {
        gcCode();
    }

    /**
     * 堆内存=20m
     * 新生代=20* 1/4 = 5m
     * from survivor = 5 * 1/5 = 1m
     * -Xmx20m -Xms20m -XX:NewRatio=3 -XX:SurvivorRatio=3 -XX:+PrintGCDetails
     */
    @Test
    public void heapRatioSetting() {
        gcCode();
    }

    /**
     * -Xmx100m -Xms100m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=log/heap.dump
     */
    @Test
    public void heapOOMDump() {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new byte[1024 * 1024]);
        }
    }

    /**
     * -XX:PermSize=20m -XX:MaxPermSize=20m -XX:+PrintGCDetails
     * 永久区大小
     */
    @Test
    public void permSize() {
        gcCode();
    }

    /**
     * -Xss500k
     */
    @Test
    public void stack() {
        gcCode();
    }


    private void gcCode() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
        System.gc();
    }

    /**
     * -verbose:gc
     * 没加int a= 0;之前，gc结果为：
     * [GC (System.gc())  72881K->66896K(125952K), 0.0055511 secs]
     * [Full GC (System.gc())  66896K->66813K(125952K), 0.0167158 secs]
     * 添加之后，gc结果为：
     * [GC (System.gc())  72881K->66912K(125952K), 0.0035348 secs]
     * [Full GC (System.gc())  66912K->1277K(125952K), 0.0146786 secs]
     * 虽然placeholder在gc发生之前已经脱离了作用域，但是gc依然没有对placeholder进行gc操作，
     * 这是因为在没有添加int a = 0;之前，栈桢中的局部变量表没有其他读写操作，placeholder原本占用
     * 的slot还没有被其他变量所复用，所以作为GC Roots一部分的局部变量表仍然保持着对它的关联。这种关联
     * 没有被打断。
     * Practical Java中推荐把不使用的对象手动赋值为null（往往对于大对象非常有用）
     */
    @Test
    public void testBigObjGC() {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
//            placeholder = null;
        }
        int a = 0; // placeholder = null 与 int a = 0;代码等效
        System.gc();
    }

}
