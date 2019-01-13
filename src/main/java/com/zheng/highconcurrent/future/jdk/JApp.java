package com.zheng.highconcurrent.future.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class JApp {
    public static void main(String[] args) throws Exception {
        Callable<String> call = new JRealData("hello world");
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(call);
        Thread.sleep(1000);
        String result = future.get();
        System.out.println("response msg: \n" + result);
    }
}
