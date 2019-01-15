package com.zheng.highconcurrent.java8;

import java.util.Random;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock是对读写锁的优化，达到：
 * 读不阻塞写，读写无障碍
 *
 * @Author zhenglian
 * @Date 2019/1/15
 */
public class StampedLockApp {

    private static class Point {
        private int x;
        private int y;
        private StampedLock lock = new StampedLock();

        public void move(int deltaX, int deltaY) {
            long stamp = lock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                lock.unlockWrite(stamp);
            }
        }

        public double distance() {
            // 先采用乐观锁获取
            long stamp = lock.tryOptimisticRead();
            int currentX = x;
            int currentY = y;
            if (!lock.validate(stamp)) {
                stamp = lock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    lock.unlockRead(stamp);
                }
            }
            System.out.println("currentX: " + currentX + ", currentY: " + currentY);
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }
    }

    public static void main(String[] args) throws Exception {
        Point point = new Point();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(
                () -> point.move(new Random().nextInt(3), new Random().nextInt(3))
            );
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        double distance = point.distance();
        System.out.println("distance: " + distance);
    }
}
