package com.zheng.highconcurrent.java8;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture是对Future功能的扩充，主要用于lamda表达式
 * 将任务完成的决定时机交给用户实现，从而达到让用户决定最终任务的完成时间
 * 同时可以通过其给定的方法api对多个CompletableFuture进行组装
 * @Author zhenglian
 * @Date 2019/1/15
 */
public class CompletableFutureApp {
    
    public static void main(String[] args) throws Exception {
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> calc(10)) // a = 10 * 10
                .thenCompose((result) -> CompletableFuture.supplyAsync(() -> calc(result))) // b = a * a 
                .thenApply((result) -> "\"" + result + "\"") // result = "{b}"
                .thenAccept(System.out::println);
        cf.get();
    }

    /**
     * 模拟耗时任务
     * @param param
     * @return
     */
    public static Integer calc(Integer param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return param * param;
    }
}
