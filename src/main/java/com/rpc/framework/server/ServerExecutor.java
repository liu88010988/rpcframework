package com.rpc.framework.server;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.rpc.framework.config.RpcConfig;
import com.rpc.framework.serialize.SerializeProtocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujiawei on 2017/4/22.
 * 服务端执行器
 */
public class ServerExecutor {
    private String serverAddress;
    private SerializeProtocol serializeProtocol = SerializeProtocol.JDKSERIALIZE;
    private static final String DELIMITER = RpcConfig.DELIMITER;
    private int parallel = RpcConfig.PARALLEL * 2;
    private static int threadNums = RpcConfig.SYSTEM_PROPERTY_THREADPOOL_THREAD_NUMS;
    private static int queueNums = RpcConfig.SYSTEM_PROPERTY_THREADPOOL_QUEUE_NUMS;
    private static volatile ListeningExecutorService threadPoolExecutor;
    private Map<String, Object> serverMap = new ConcurrentHashMap<>();

    private ServerExecutor() {

    }

    private static class ServerExecutorHolder {
        private static ServerExecutor instance = new ServerExecutor();
    }

    public static ServerExecutor getInstance() {
        return ServerExecutorHolder.instance;
    }

    public Map<String, Object> getServerMap() {
        return serverMap;
    }

    public void setServerMap(Map<String, Object> serverMap) {
        this.serverMap = serverMap;
    }

    public void start() {

    }

    public void stop() {

    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public SerializeProtocol getSerializeProtocol() {
        return serializeProtocol;
    }

    public void setSerializeProtocol(SerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }
}
