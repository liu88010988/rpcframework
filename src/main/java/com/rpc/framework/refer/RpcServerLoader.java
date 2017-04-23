package com.rpc.framework.refer;

import com.google.common.util.concurrent.*;
import com.rpc.framework.biz.RpcClientBizHandler;
import com.rpc.framework.config.RpcConfig;
import com.rpc.framework.thread.RpcThreadPool;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcServerLoader {
    private String ipAddr;
    private String protocol;
    private NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private ListeningExecutorService executorService = MoreExecutors.listeningDecorator(RpcThreadPool.getExecutor(RpcConfig.SYSTEM_PROPERTY_THREADPOOL_THREAD_NUMS, RpcConfig.SYSTEM_PROPERTY_THREADPOOL_QUEUE_NUMS));
    private RpcClientBizHandler handler;
    private Lock lock = new ReentrantLock();
    private Condition handlerCondition = lock.newCondition();
    private Condition connectCondition = lock.newCondition();

    private RpcServerLoader() {

    }

    private static class RpcServerLoaderHolder {
        private static RpcServerLoader instance = new RpcServerLoader();
    }

    public static RpcServerLoader getInstance() {
        return RpcServerLoaderHolder.instance;
    }

    public void load() {
        ListenableFuture<Boolean> future = executorService.submit(new RpcClientTask(eventLoopGroup, ipAddr, protocol));
        Futures.addCallback(future, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                lock.lock();
                try {
                    if (handler == null) {
                        handlerCondition.await();
                    }
                    if (result && handler != null) {
                        connectCondition.signalAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void unload() {
        eventLoopGroup.shutdownGracefully();
        executorService.shutdown();
        if (handler != null) {
            handler.close();
        }
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public RpcServerLoader setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public RpcServerLoader setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public RpcClientBizHandler getHandler() throws InterruptedException {
        lock.lock();
        try {
            if (handler == null) {
                connectCondition.await();
            }
            return handler;
        } finally {
            lock.unlock();
        }
    }

    public void setHandler(RpcClientBizHandler handler) {
        lock.lock();
        try {
            this.handler = handler;
            handlerCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
