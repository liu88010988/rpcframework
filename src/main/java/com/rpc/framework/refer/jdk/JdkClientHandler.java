package com.rpc.framework.refer.jdk;

import com.rpc.framework.biz.RpcClientBizHandler;
import com.rpc.framework.refer.RpcClientHandler;
import com.rpc.framework.serialize.MessageUtil;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class JdkClientHandler implements RpcClientHandler {
    @Override
    public void handle(ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, MessageUtil.MESSAGE_LENGTH, 0, MessageUtil.MESSAGE_LENGTH));
        pipeline.addLast(new LengthFieldPrepender(MessageUtil.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(getClass().getClassLoader())));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new RpcClientBizHandler());
    }
}
