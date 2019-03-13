package com.zheng.jvm;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * -Xmx50m -Xms50m -XX:PermSize=1k -XX:+PrintGCDetails
     * [GC (Allocation Failure) [ParOldGen: 1233K->1237K(34304K)] 
     */
    @Test
    public void permOOM() throws Exception {
        List<CglibBean> list = new ArrayList<>();
        for(int i=0;i<100000;i++){
            // 设置类成员属性  
            Map<String, Object> propertyMap = new HashMap();
            propertyMap.put("id" + i, Class.forName("java.lang.Integer"));
            propertyMap.put("name" + i, Class.forName("java.lang.String"));
            propertyMap.put("address" + i, Class.forName("java.lang.String"));
            // 生成动态 Bean
            CglibBean bean = new CglibBean(propertyMap);
            list.add(bean);
            System.gc();
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
