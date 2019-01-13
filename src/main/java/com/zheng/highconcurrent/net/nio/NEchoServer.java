package com.zheng.highconcurrent.net.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zhenglian
 * @Date 2019/1/13
 */
public class NEchoServer {
    
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    private static final Map<Socket, Long> timeStat = new ConcurrentHashMap<>();

    private static class EchoClient {

        private LinkedList<ByteBuffer> outq;

        public EchoClient() {
            outq = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutq() {
            return outq;
        }

        public void enqueue(ByteBuffer buffer) {
            outq.addFirst(buffer);
        }
    }
    
    private static class MsgHandler implements Runnable {
        private SelectionKey key;
        private ByteBuffer buffer;
        
        public MsgHandler(SelectionKey key,  ByteBuffer buffer) {
            this.key = key;
            this.buffer = buffer;
        }
        
        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) key.attachment();
            echoClient.enqueue(buffer);
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            // 强迫selector立即返回,因为当前服务器线程在accept客户端请求后又进入了select()阻塞等待中
            key.selector().wakeup();
        }
    }
    
    public static void main(String[] args) {
        try {
            ServerSocketChannel ss = ServerSocketChannel.open();
            // 非阻塞模式
            ss.configureBlocking(false);
            ss.socket().bind(new InetSocketAddress("localhost", 8989));

            Selector selector = SelectorProvider.provider().openSelector();
            ss.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start listening on 8989...");
            while(true) {
                // 检查channel数据就绪状态
                selector.select();
                // 如果能继续往下执行，说明其中存在至少一个channel数据准备已经就绪
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 将当前正在处理的key移除，防止下次重复处理
                    iterator.remove();
                    if (key.isAcceptable()) {
                        doAccept(key);
                    } else if (key.isValid() && key.isReadable()) {
                        // 读准备好了，这里的数据准备直接由selectionKey做好了，线程只需要负责读取数据，不需要准备数据
                        SocketChannel channel = (SocketChannel) key.channel();
                        timeStat.putIfAbsent(channel.socket(), System.currentTimeMillis());
                        doRead(key);
                    } else if(key.isValid() && key.isWritable()){
                        doWrite(key);
                        SocketChannel channel = (SocketChannel) key.channel();
                        long start = timeStat.remove(channel.socket());
                        System.out.println("spend: " + (System.currentTimeMillis() - start) + "ms");
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doWrite(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        EchoClient echoClient = (EchoClient) key.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutq();
        ByteBuffer buffer = outq.getLast();
        try {
            int len = channel.write(buffer);
            if (len < 0) {
                disconnect(key);
                return;
            }
            if (buffer.remaining() == 0) {
                outq.removeLast();
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect(key);
        }
        
        if (outq.size() == 0) {
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    /**
     * 数据读取
     * @param key
     */
    private static void doRead(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len;
        try {
            len = channel.read(buffer);
            if (len < 0) {
                disconnect(key);
                return;
            }
        } catch (IOException e) {
            System.out.println("fail to read from client.");
            e.printStackTrace();
            disconnect(key);
        }
        
        // handle byte buffer
        buffer.flip();
        pool.execute(new MsgHandler(key, buffer));
    }

    private static void disconnect(SelectionKey key) {
        key.cancel();
    }

    /**
     * 处理接收到的客户端连接请求
     * @param key
     */
    private static void doAccept(SelectionKey key) {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = channel.accept();
            clientChannel.configureBlocking(false);
            SelectionKey registerKey = clientChannel.register(key.selector(), SelectionKey.OP_READ);
            EchoClient echoClient = new EchoClient();
            registerKey.attach(echoClient);
            InetAddress inetAddress = clientChannel.socket().getInetAddress();
            System.out.println("accepted from host: " + inetAddress);
        } catch (IOException e) {
            System.out.println("fail to accept new client.");
            e.printStackTrace();
        }

    }
}
