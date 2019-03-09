package com.zheng.jvm;

/**
 * @Author zhenglian
 * @Date 2019/3/9
 */
public class SimpleCalcApp {
    
    // javap -c SimpleCalcApp.class
    /*
    * public class com.zheng.jvm.SImpleCalcApp {
  public com.zheng.jvm.SImpleCalcApp();
    Code:
       0: aload_0
       1: invokespecial #1// Method java/lang/Object."<init>":()V
       4: return

  public static int add(int, int);
    Code:
       0: iconst_0  # 0压栈
       1: istore_2  # 弹出int，并保存到局部变量2中
       2: iload_0   # 变量0入操作数栈
       3: iload_1   # 变量1入操作数栈
       4: iadd      # 弹出操作数中的栈顶两个元素，进行加和并入栈
       5: istore_2  # 弹出结果并保存到变量2中
       6: iload_2   # 将变量2压栈
       7: ireturn   # 返回栈顶结果
}

    * */
    
    public static int add(int a, int b) {
        int c = 0;
        c = a+b;
        return c;
    }
}
