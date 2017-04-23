package com.rpc.framework.refer;

import com.google.common.reflect.Reflection;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcClientExecutor {
    private RpcServerLoader loader = RpcServerLoader.getInstance();

    private RpcClientExecutor() {

    }

    private static class RpcClientExecutorHolder {
        private static RpcClientExecutor instance = new RpcClientExecutor();
    }

    public static RpcClientExecutor getInstance() {
        return RpcClientExecutorHolder.instance;
    }

    public void start(String ipAdd, String protocol) {
        loader.setIpAddr(ipAdd).setProtocol(protocol).load();
    }

    public void stop() {
        loader.unload();
    }

    public Object execute(Class<?> clz) {
        return Reflection.newProxy(clz, new RpcIvokeHandler());
    }
}
