package com.zheng.highconcurrent.future.custom;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class RealData implements Data {
    
    private StringBuilder builder = new StringBuilder();
    
    public RealData(String msg) {
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
    }

    @Override
    public String getResult() {
        return builder.toString();
    }
}
