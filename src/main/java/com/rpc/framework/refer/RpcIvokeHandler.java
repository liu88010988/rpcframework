package com.rpc.framework.refer;

import com.google.common.reflect.AbstractInvocationHandler;
import com.rpc.framework.model.MessageRequest;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcIvokeHandler extends AbstractInvocationHandler {
    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setArgs(args);
        RpcCallBack callBack = RpcServerLoader.getInstance().getHandler().send(request);
        return callBack.start();
    }
}
