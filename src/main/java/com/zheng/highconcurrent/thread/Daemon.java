package com.zheng.highconcurrent.thread;

/**
 * @Author zhenglian
 * @Date 2019/1/6
 */
public class Daemon {
    
    public static class DaemonThread extends Thread {
        @Override
        public void run() {
            while(true) {
                System.out.println("i'm alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Thread t = new DaemonThread();
        // 如果应用程序里面只包含守护线程，那么应用程序会停止，
        // 应用程序运行的是非守护线程，守护线程只是起辅助作用
        t.setDaemon(true);
        t.start();
    }
    
}
