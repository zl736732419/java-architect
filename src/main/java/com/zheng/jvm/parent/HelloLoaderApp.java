package com.zheng.jvm.parent;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2019年03月15日			zhenglian			    Initial.
 *
 * </pre>
 */
public class HelloLoaderApp {
    public static void main(String[] args) {
        /*
            默认 打印 i'm app loader，因为jvm双亲委派模型加载类是由父loader->子loader的顺序加载的，这里父loader无法实例化HelloLoader
            由AppClassLoader实例化，从classpath下找到HelloLoader并进行实例化
            
            如果添加VM参数：-Xbootclasspath/a:C:\Users\Administrator\Desktop\tmp，并重新生成HelloLoader类字节码，再次运行将会打印
            tmp目录下的HelloLoader内容，因为这个目录已经加入到BootClassLoader类加载的路径中，所以可以找到并实例化，本地classpath类将无法
            生效
         */
        new HelloLoader().print();
    }
}
