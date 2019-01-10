package com.zheng.highconcurrent.nonlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 在不改变原有属性类型基础上，对成员属性做安全更新
 * 主要是为了兼容接口，通过updater反射的方式去安全更新对象实例属性值
 * @Author zhenglian
 * @Date 2019/1/10
 */
public class AtomicIntegerFieldUpdaterApp {
    
    private static class Person {
        int id;
        volatile int score;
    }
    
    private static AtomicIntegerFieldUpdater<Person> updater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "score");
    
    // 与属性score最后得分做比较，确保并发安全性
    private static AtomicInteger allScore = new AtomicInteger(0);
    
    public static void main(String[] args) throws Exception {
    
        Person person = new Person();
        
        Thread[] ts = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            ts[i] = new Thread(()-> {
                if (Math.random() > 0.4) {
                    updater.incrementAndGet(person);
                    allScore.incrementAndGet();
                }
            });
            ts[i].start();
        }
        
        // 等待所有线程都运行完成之后获取最终结果
        for (int i = 0; i < 1000; i++) {
            ts[i].join();
        }
        System.out.println("updater score: " + person.score);
        System.out.println("compare score: " + allScore.get());
    }
    
    
}
