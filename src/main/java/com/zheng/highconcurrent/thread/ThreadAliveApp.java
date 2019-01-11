package com.zheng.highconcurrent.thread;

/**
 */
public class ThreadAliveApp {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("sub thread start...");
            try {
                Thread.sleep(10000); // 实际上是通过wait让当前线程阻塞，超过时间后由JVM唤醒
            } catch (InterruptedException e) {
            }
            System.out.println("finish");
        });
        thread.setName("thread-01");
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(thread.isAlive());

    }
}
