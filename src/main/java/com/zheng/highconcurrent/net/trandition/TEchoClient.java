package com.zheng.highconcurrent.net.trandition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class TEchoClient {
    private static final long time = 1000*1000*1000;
    
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost", 8989));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 这里模拟客户端业务处理缓慢
            writer.print("H");
            LockSupport.parkNanos(time);
            writer.print("e");
            LockSupport.parkNanos(time);
            writer.print("l");
            LockSupport.parkNanos(time);
            writer.print("l");
            LockSupport.parkNanos(time);
            writer.print("o");
            LockSupport.parkNanos(time);
            writer.print("!");
            LockSupport.parkNanos(time);
            writer.println();
            writer.flush();
            String line = reader.readLine();
            System.out.println("response: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
