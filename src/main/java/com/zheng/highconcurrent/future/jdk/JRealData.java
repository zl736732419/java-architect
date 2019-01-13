package com.zheng.highconcurrent.future.jdk;

import java.util.concurrent.Callable;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class JRealData implements Callable<String> {
    private StringBuilder builder = new StringBuilder();
    private String msg;
    public JRealData(String msg) {
        this.msg = msg;
    }
    @Override
    public String call() throws Exception {
        // 这里构造真实数据，可能会耗费一定时间
        System.out.println("create real data...");
        for (int i = 0; i < 10; i++) {
            builder.append(msg).append("\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
