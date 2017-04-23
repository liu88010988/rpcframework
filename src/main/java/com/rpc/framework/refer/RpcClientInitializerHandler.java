package com.rpc.framework.refer;

import com.rpc.framework.serialize.SerializeProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcClientInitializerHandler extends ChannelInitializer {
    private String protocol;
    private RpcClientSerializeSelector serializeSelector;
    private Channel channel;

    public RpcClientInitializerHandler(String protocol) {
        this.protocol = protocol;
        serializeSelector = new RpcClientSerializeSelector();
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        this.channel = ch;
        serializeSelector.select(Enum.valueOf(SerializeProtocol.class, protocol), ch.pipeline());
    }
}
