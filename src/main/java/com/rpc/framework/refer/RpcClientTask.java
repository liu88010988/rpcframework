package com.rpc.framework.refer;

import com.rpc.framework.biz.RpcClientBizHandler;
import com.rpc.framework.config.RpcConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Callable;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcClientTask implements Callable<Boolean> {
    private String ipAddr;
    private String protocol;
    private EventLoopGroup eventLoopGroup;

    public RpcClientTask(EventLoopGroup eventLoopGroup, String ipAddr, String protocol) {
        this.eventLoopGroup = eventLoopGroup;
        this.ipAddr = ipAddr;
        this.protocol = protocol;
    }

    @Override
    public Boolean call() throws Exception {
        String[] ipAddrs = ipAddr.split(RpcConfig.DELIMITER);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new RpcClientInitializerHandler(protocol));
        ChannelFuture future = bootstrap.connect(ipAddrs[0], Integer.valueOf(ipAddrs[1])).sync();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    RpcClientBizHandler handler = future.channel().pipeline().get(RpcClientBizHandler.class);
                    RpcServerLoader.getInstance().setHandler(handler);
                }
            }
        });
        return Boolean.TRUE;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public void setEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
    }
}
