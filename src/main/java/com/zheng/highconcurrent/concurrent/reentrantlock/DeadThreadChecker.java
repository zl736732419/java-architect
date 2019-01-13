package com.zheng.highconcurrent.concurrent.reentrantlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Author zhenglian
 * @Date 2019/1/12
 */
public class DeadThreadChecker {
    private static ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
    private static Runnable deadLockCheck = () -> {
        while (true) {
            long[] threadIds = mxBean.findDeadlockedThreads();
            if (null != threadIds) {
                ThreadInfo[] infos = mxBean.getThreadInfo(threadIds);
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    for (int i = 0; i < infos.length; i++) {
                        if (t.getId() == infos[i].getThreadId()) {
                            t.interrupt();
                        }
                    }
                }
            }
            // 每隔5秒运行一次死锁检测
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static void check() {
        Thread checker = new Thread(deadLockCheck);
        checker.setDaemon(true);
        checker.setName("dead lock checker thread");
        checker.start();
    }
}
