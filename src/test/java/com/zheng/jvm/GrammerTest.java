package com.zheng.jvm;

import org.junit.Test;

/**
 * @Author zhenglian
 * @Date 2019/3/6
 */
public class GrammerTest {
    
    class Value {
        int val;
    }
    
    @Test
    public void basic() {
        // 值传递
        int i1 = 3;
        int i2 = i1;
        i2 = 4;
        System.out.println("i1 == " + i1);
        System.out.println(" but i2 == " + i2);

        // 引用传递
        Value v1 = new Value(); 
        v1.val = 5;
        Value v2 = v1;
        v2.val = 6;
        System.out.println("v1.val == " + v1.val);
        System.out.println("and v2.val == " + v2.val);
    }
    
    @Test
    public void printByte() {
        int a = -127;
        int t;
        for (int i = 0; i < 32; i++) {
            t = (a & 0x80000000>>>i) >>> (31 - i);
            System.out.print(t);
        }
    }
    
    @Test
    public void validNumber() {
//        int a=0xDada_Cafe; // ok
//        float b=0x1.fffffeP+127f; // ok
//        float c=1996; // ok
//        float d=1996.3; // 1996.3d
//        int f=9999e2; // float
//        double g=33e2; // ok
//        float h=0x1.fffep-12f; // ok
//        float i=1.fffep-12f; // error
//        long p=0b1_1_1_0_1; // ok
//        long q=0b1_1_1_0_2; // error

    }
}
