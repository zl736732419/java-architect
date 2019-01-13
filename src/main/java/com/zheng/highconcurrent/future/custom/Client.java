package com.zheng.highconcurrent.future.custom;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class Client {
    public Data request(final String queryStr) {
        FutureData fd = new FutureData();
        // 执行具体取数逻辑，因为逻辑可能耗时比较长，所以使用异步加载的方式
        new Thread(()->{
            RealData rd = new RealData(queryStr);
            fd.setRealData(rd);
        }).start();
        
        return fd;
    }
}
