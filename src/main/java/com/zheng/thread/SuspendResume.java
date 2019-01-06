package com.zheng.thread;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;

/**
 * @Author zhenglian
 * @Date 2019/1/6
 */
public class SuspendResume {
    
    public static Object lock = new Object();
    
    private static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    private static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    
    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("in " + getName());
                Thread.currentThread().suspend();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.resume();
        t2.resume(); // t2 resume方法先于t2 suspend方法执行，导致t2会进入阻塞并永久阻塞
    }
}
