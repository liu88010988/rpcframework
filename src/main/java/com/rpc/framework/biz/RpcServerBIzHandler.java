package com.rpc.framework.biz;

import com.rpc.framework.model.MessageRequest;
import com.rpc.framework.model.MessageResponse;
import com.rpc.framework.server.RpcServerExecutor;
import com.rpc.framework.server.RpcServerTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcServerBIzHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Object> serverMap;

    public RpcServerBIzHandler(Map<String, Object> serverMap) {
        this.serverMap = serverMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRequest request = (MessageRequest) msg;
        handlRequest(request, ctx);
        ctx.fireChannelRead(msg);
    }

    private void handlRequest(MessageRequest request, ChannelHandlerContext ctx) {
        MessageResponse response = new MessageResponse();
        RpcServerExecutor.submit(new RpcServerTask(request, response, serverMap), ctx, request, response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
