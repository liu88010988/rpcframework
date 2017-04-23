package com.rpc.framework.server;

import com.google.common.util.concurrent.*;
import com.rpc.framework.config.RpcConfig;
import com.rpc.framework.model.MessageRequest;
import com.rpc.framework.model.MessageResponse;
import com.rpc.framework.serialize.SerializeProtocol;
import com.rpc.framework.thread.RpcThreadFactory;
import com.rpc.framework.thread.RpcThreadPool;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujiawei on 2017/4/22.
 * 服务端执行器
 */
public class RpcServerExecutor {
    private String serverAddress;
    private SerializeProtocol serializeProtocol = SerializeProtocol.JDKSERIALIZE;
    private static final String DELIMITER = RpcConfig.DELIMITER;
    private static int threadNums = RpcConfig.SYSTEM_PROPERTY_THREADPOOL_THREAD_NUMS;
    private static int queueNums = RpcConfig.SYSTEM_PROPERTY_THREADPOOL_QUEUE_NUMS;
    private static volatile ListeningExecutorService threadPoolExecutor;
    private Map<String, Object> serverMap = new ConcurrentHashMap<>();
    private NioEventLoopGroup boss = new NioEventLoopGroup();
    private NioEventLoopGroup worker = new NioEventLoopGroup(RpcConfig.PARALLEL * 2, new RpcThreadFactory("netty-thread"));

    private RpcServerExecutor() {

    }

    private static class ServerExecutorHolder {
        private static RpcServerExecutor instance = new RpcServerExecutor();
    }

    public static RpcServerExecutor getInstance() {
        return ServerExecutorHolder.instance;
    }

    public Map<String, Object> getServerMap() {
        return serverMap;
    }

    public static void submit(Callable<Boolean> task, final ChannelHandlerContext ctx, final MessageRequest request, final MessageResponse response) {
        if (threadPoolExecutor == null) {
            synchronized (RpcServerExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = MoreExecutors.listeningDecorator(RpcThreadPool.getExecutor(threadNums, queueNums));
                }
            }
        }
        ListenableFuture<Boolean> future = threadPoolExecutor.submit(task);
        Futures.addCallback(future, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        System.out.println("write response success msgId:" + request.getMessageId());
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, threadPoolExecutor);
    }

    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker).channel(NioSctpServerChannel.class)
                    .childHandler(new RpcServerChannelInitializer(serverMap).setProtocol(serializeProtocol))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            if (StringUtils.isNotBlank(serverAddress)) {
                String[] address = serverAddress.split(DELIMITER);
                if (address.length == 2) {
                    ChannelFuture future = bootstrap.bind(address[0], Integer.valueOf(address[1])).sync();
                    future.channel().closeFuture().sync();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setSerializeProtocol(SerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }
}
