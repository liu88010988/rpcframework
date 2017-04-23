package com.rpc.framework.server.jdk;

import com.rpc.framework.biz.RpcServerBIzHandler;
import com.rpc.framework.serialize.MessageUtil;
import com.rpc.framework.server.RpcServerHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Map;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class JdkServerHandler implements RpcServerHandler {
    @Override
    public void handle(Map<String, Object> handlerMap, ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, MessageUtil.MESSAGE_LENGTH, 0, MessageUtil.MESSAGE_LENGTH));
        pipeline.addLast(new LengthFieldPrepender(MessageUtil.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(getClass().getClassLoader())));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new RpcServerBIzHandler(handlerMap));
    }
}
