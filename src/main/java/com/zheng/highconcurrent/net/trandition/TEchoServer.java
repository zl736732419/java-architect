package com.zheng.highconcurrent.net.trandition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class TEchoServer {
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    
    private static class MsgHandler implements Runnable {
        private Socket socket;
        public MsgHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());
                long start = System.currentTimeMillis();
                String line;
                // 传统网络编程，对socket数据的准备和读取都放在了处理线程中，
                // 这样导致线程资源浪费，线程必须等待数据准备好之后才能读取，
                // 并且在数据准备这段时间线程也处于阻塞状态
                while ((line = reader.readLine()) != null) {
                    System.out.println("request msg: " + line);
                    writer.println(line);
                    writer.flush();
                }
                long end = System.currentTimeMillis();
                System.out.println("spend: " + (end - start) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8989);
            System.out.println("server start listen on 8989...");
            while (true) {
                Socket socket = ss.accept();
                System.out.println("handle client request,  socket is " + socket);
                pool.submit(new MsgHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
