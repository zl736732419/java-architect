package com.zheng.jvm;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @Author zhenglian
 * @Date 2019/3/31
 */
public class ExtendApp {
    
    static class GrandFather {
        void thinking() {
            System.out.println("grand father");
        }
    }
    
    static class Father extends GrandFather {
        void thinking() {
            System.out.println("father");
        }
    }
    
    static class Son extends Father {
        void thinking() {
            super.thinking();
        }
    }
    
    static class Son1 extends Father {
        void thinking() {
            try {
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);
            } catch (Throwable e) {
            }
        }
    }
    
    @Test
    public void test() {
//        Son son = new Son();
//        son.thinking();

        Son1 son1 = new Son1();
        son1.thinking();
    }
}
