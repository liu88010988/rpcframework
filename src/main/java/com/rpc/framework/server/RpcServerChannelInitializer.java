package com.rpc.framework.server;

import com.rpc.framework.serialize.RpcSerializeSelector;
import com.rpc.framework.serialize.SerializeProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

import java.util.Map;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcServerChannelInitializer extends ChannelInitializer {
    private SerializeProtocol protocol;
    private RpcSerializeSelector selector;

    public RpcServerChannelInitializer(Map<String, Object> handlerMap) {
        setSelector(new RpcServerSerializeSelector(handlerMap));
    }

    public RpcServerChannelInitializer setProtocol(SerializeProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    private RpcServerChannelInitializer setSelector(RpcSerializeSelector selector) {
        this.selector = selector;
        return this;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        selector.select(protocol, pipeline);
    }
}
