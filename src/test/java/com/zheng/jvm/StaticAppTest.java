package com.zheng.jvm;

import org.junit.Test;

/**
 * @Author zhenglian
 * @Date 2019/3/28
 */
public class StaticAppTest {
    
    private static class SuperClass {
        static {
            System.out.println("super class");
        }
        
        public static int value = 123;
    }
    
    private static class SubClass extends SuperClass {
        static {
            System.out.println("sub class");
        }
    }

    /**
     * 对于静态字段，只有直接定义这个字段的类才会被初始化
     */
    @Test
    public void staticFieldTest() {
        System.out.println(SubClass.value);
    }

    /**
     * -XX:+TraceClassLoading
     * 数组对象初始化，会触发一个由jvm直接生成的数组对象初始化，并不会对元素对象进行初始化
     */
    @Test
    public void arrTest() {
        SuperClass[] cls = new SuperClass[10];
    }
    
    private static class ConstantClass {
        static {
            System.out.println("Constant class init");
        }
        
        public static final String HELLO_WORLD = "hello world";
        public static final Integer NUM = 123;
    }

    /**
     * 对于常量来说，经过常量编译期传播优化，HELLO_WORLD常量已经传播到StaticAppTest这个类的常量池中
     * 以后对这个常量的访问与ConstantClass无半点关系
     */
    @Test
    public void constantTest() {
        System.out.println(ConstantClass.HELLO_WORLD);
        // 常量池只是针对字符串而言，对其他类型无效,这里会输出Constant class init
        System.out.println(ConstantClass.NUM);
    }
    
}
