package com.zheng.highconcurrent.future;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        Data data = client.request("hello world");
        // 处理自己的业务逻辑
        System.out.println("处理主线程业务逻辑....");
        Thread.sleep(1000);
        System.out.println("主线程逻辑处理完毕, 获取请求数据结果: ");
        
        // 获取请求的结果数据
        String result = data.getResult();
        System.out.println("repsonse msg: \n" + result);
    }
}
