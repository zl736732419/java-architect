package com.zheng.highconcurrent.thread;

/**
 */
public class ThreadAliveApp {
    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println("sub thread start...");
            try {
                Thread.sleep(10000); // 实际上是通过wait让当前线程阻塞，超过时间后由JVM唤醒
            } catch (InterruptedException e) {
            }
            System.out.println("finish");
        };
        Thread t1 = new Thread(r);
        t1.setName("t1");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(t1.isAlive());

        Thread t2 = new Thread(r);
        t2.setName("t2");
        t2.start();
        // 统计当前线程分组中活跃线程的个数
        System.out.println(t1.activeCount());
        
    }
}
