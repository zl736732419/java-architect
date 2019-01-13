package com.zheng.highconcurrent.future.custom;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class FutureData implements Data {
    private RealData realData;
    private boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        this.isReady = true;
        notifyAll();
    }
    
    public synchronized String getResultSafty() {
        if (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = null;
        if (realData != null) {
            result = realData.getResult();
        }
        return result;
    }
    
    @Override
    public String getResult() {
        return getResultSafty();
    }
}
