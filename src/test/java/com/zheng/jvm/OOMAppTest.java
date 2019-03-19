package com.zheng.jvm;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhenglian
 * @Date 2019/3/13
 */
public class OOMAppTest {

    /**
     * 堆溢出
     * -Xmx5m -XX:+PrintGCDetails
     * java.lang.OutOfMemoryError: Java heap space
     * 解决方式：
     * 增加堆内存
     * 及时释放内存
     */
    @Test
    public void heapOOM() {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            list.add(new byte[1024 * 1024]);
        }
    }

    /**
     * 永久区溢出
     * 解决方式：
     * 增大Perm区
     * 允许class回收
     * [Full GC (Ergonomics) [PSYoungGen: 5120K->0K(88576K)] 
     * 这里说明永久区已经被占满，无法分配多余内存
     * [ParOldGen: 173961K->161154K(347136K)] 179081K->161154K(435712K), 
     * 
     * [Metaspace: 4819K->4819K(1056768K)], 1.4138791 secs] 
     * [Times: user=3.41 sys=0.05, real=1.41 secs] 
     * [GC (Allocation Failure) 
     * [PSYoungGen: 83456K->5120K(104960K)] 244610K->244762K(452096K), 0.2415082 secs] 
     * [Times: user=0.52 sys=0.05, real=0.24 secs] 
     */
    @Test
    public void permOOM() throws Exception {
        List<String> list = new ArrayList<>();
        int i = 0;
        while(true) {
            list.add(String.valueOf(i++).intern());
        }
    }

    public static class SleepThread implements Runnable{
        public void run(){
            try {
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 栈溢出
     * -Xmx2g -Xms2g -Xss100m
     * An unrecoverable stack overflow has occurred.
     * 解决方式：
     * 减小堆内存
     * 减小栈内存
     * 栈内存越小，说明为每个线程创建的内存就小，同时减少堆内存，
     * 那么更多的内存可以用于线程分配
     */
    @Test
    public void stackOOM() {
        for(int i=0;i<10000;i++){
            new Thread(new SleepThread(),"Thread"+i).start();
            System.out.println("Thread"+i+" created");
        }
    }
    
    /**
     * 直接内存溢出,直接内存jvm不会主动触发gc
     *  -Xmx7g -XX:+PrintGCDetails
     * [GC (Allocation Failure) ...
     * 解决方式：减少堆内存，有意触发GC (System.gc())
     */
    @Test
    public void directOOM() {
        for (int i = 0; i < 2048; i++) {
            ByteBuffer allocate = ByteBuffer.allocate(1024 * 1024 * 1024);
            byte[] array = allocate.array();
            System.out.println(array.length);
            // 手动触发gc
//            System.gc();
        }
    }
}
