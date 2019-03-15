package com.zheng.jvm.parent;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  强制由子class loader加载类，打破双亲模型
 *  Revision History
 *  Date,					Who,					What;
 *  2019年03月15日			zhenglian			    Initial.
 *
 * </pre>
 */
public class HelloLoaderApp2 {
    public static void main(String[] args) throws Exception {
        String classDir = "D:\\projects\\java-architect\\target\\classes";
        String pkg = "com.zheng.jvm.parent.HelloLoader";
        byte[] bytes = loadBytesFromFile(classDir, pkg);

        ClassLoader cl = HelloLoaderApp2.class.getClassLoader();
        Method md_defineClass=ClassLoader.class.getDeclaredMethod("defineClass", byte[].class,int.class,int.class);
        md_defineClass.setAccessible(true);
        md_defineClass.invoke(cl,bytes, 0, bytes.length);
        md_defineClass.setAccessible(false);
        new HelloLoader().print();
    }

    private static byte[] loadBytesFromFile(String classDir, String pkg) throws Exception {
        String pckPath = pkg.replaceAll("\\.", "\\\\");
        String path = new StringBuilder(classDir)
                .append("\\")
                .append(pckPath)
                .append(".class").toString();
        File file = new File(path);
        InputStream input = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        input.read(bytes);
        input.close();
        return bytes;
    }
}
