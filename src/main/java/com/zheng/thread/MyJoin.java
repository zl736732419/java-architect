package com.zheng.thread;

/**
 * @Author zhenglian
 * @Date 2019/1/6
 */
public class MyJoin {
    
    public static int i = 1;
    
    public static class JoinThread extends Thread {
        @Override
        public void run() {
            for (; i<=100; i++) {
                System.out.println(i);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        JoinThread t1 = new JoinThread();
        t1.start();
        t1.join(); // 主线程等待t1线程执行完成之后再执行，执行完成后由底层C代码调用notifyAll唤醒，
        // 所以不建议在应用程序中使用当前线程的wait, notify, notifyAll方法来实现业务逻辑
        System.out.println("finished");
    }
}
