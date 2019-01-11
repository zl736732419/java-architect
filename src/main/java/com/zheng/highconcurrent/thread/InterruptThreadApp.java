package com.zheng.highconcurrent.thread;

/**
 * 
 */
public class InterruptThreadApp {
    public static void main(String[] args) {
//        sleepInterrupt();
        loopInterrupt();
    }

    /**
     * 非阻塞线程，调用interrupt()方法只是设置线程的中断标志
     * 程序需要通过isInterrupt()方法来判断当前线程是否被中断，并主动退出业务执行逻辑
     */
    private static void loopInterrupt() {
        Thread thread = new Thread(() -> {
            System.out.println("sub thread start...");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("current thread is interrupted");
                    break;
                }
                System.out.println(1);
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                thread.interrupt();
            }
        }
    }

    /**
     * 阻塞的线程被中断时会触发InterruptedException
     */
    private static void sleepInterrupt() {
        Thread thread = new Thread(() -> {
            System.out.println("sub thread start...");
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                System.out.println("interrupt handle");
                // 重新设置中断标识
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                thread.interrupt();
            }
        }
    }
}
