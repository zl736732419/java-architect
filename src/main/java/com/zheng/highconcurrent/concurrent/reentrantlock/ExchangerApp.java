package com.zheng.highconcurrent.concurrent.reentrantlock;

/**
 * @Author zhenglian
 * @Date 2019/1/26
 */
public class ExchangerApp {
    public static void main(String[] args) {
        seq();
    }

    private static void seq() {

        int MMASK = 0xff; // 255
        int SEQ = MMASK + 1; // 256
        int NCPU = Runtime.getRuntime().availableProcessors();
        int FULL = (NCPU >= (MMASK << 1)) ? MMASK : NCPU >>> 1; // 2
        int SPINS = 1 << 10; // 1024
        System.out.println(SEQ);
    }
}
