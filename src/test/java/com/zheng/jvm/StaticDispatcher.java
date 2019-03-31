package com.zheng.jvm;

import org.junit.Test;

/**
 * @Author zhenglian
 * @Date 2019/3/30
 */
public class StaticDispatcher {
    static abstract class Human {
    }
    
    static class Man extends Human {
        
    }
    
    static class Woman extends Human {
        
    }
    
    public void sayHello(Human human) {
        System.out.println("hello guy");
    }

    public void sayHello(Man man) {
        System.out.println("hello gentleman");
    }

    public void sayHello(Woman woman) {
        System.out.println("hello lady");
    }
    
    @Test
    public void test() {
        Human man = new Man();
        Human woman = new Woman();
        sayHello((Man) man);
        sayHello((Woman) woman);
    }
}
