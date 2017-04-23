package com.rpc.framework.biz;

import com.rpc.framework.model.MessageRequest;
import com.rpc.framework.model.MessageResponse;
import com.rpc.framework.refer.RpcCallBack;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcClientBizHandler extends ChannelInboundHandlerAdapter {
    private volatile Channel channel;
    private Map<String, RpcCallBack> callBackMap = new ConcurrentHashMap<>();

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListeners(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageResponse response = (MessageResponse) msg;
        RpcCallBack callBack = callBackMap.get(response.getMessageId());
        if (callBack != null) {
            callBack.over(response.getResult());
            callBackMap.remove(response.getMessageId());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public RpcCallBack send(MessageRequest request) {
        RpcCallBack callBack = new RpcCallBack();
        callBackMap.put(request.getMessageId(), callBack);
        channel.writeAndFlush(request);
        return callBack;
    }
}
