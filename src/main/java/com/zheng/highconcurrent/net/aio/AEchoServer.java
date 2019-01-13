package com.zheng.highconcurrent.net.aio;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class AEchoServer {

    public static void main(String[] args) throws Exception {
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withFixedThreadPool(Runtime.getRuntime().availableProcessors(), Executors.defaultThreadFactory());
        final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(channelGroup);
        //设置Socket选项  
        server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        
        server.bind(new InetSocketAddress(8989), 80);
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                buffer.clear();
                Future<Integer> writeResult = null;
                try {
                    long start = System.currentTimeMillis();
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    writeResult = result.write(buffer);
                    long end = System.currentTimeMillis();
                    System.out.println("spend: " + (end - start) + "ms");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    // 开始下一轮等待客户端连接
                    server.accept(null, this);
                    try {
                        // 等待write数据完毕
                        writeResult.get();
                        result.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed: " + exc);
            }
        });

    }
}
