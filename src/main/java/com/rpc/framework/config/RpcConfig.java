package com.rpc.framework.config;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcConfig {
    public static final String SYSTEM_PROPERTY_THREADPOOL_QUEUE_NAME_ATTR = "com.rpc.parallel.queue";
    public static final long SYSTEM_PROPERTY_MESSAGE_CALLBACK_TIMEOUT = Long.getLong("netty-default-msg-timeout", 10 * 1000L);
    public static final int SYSTEM_PROPERTY_THREADPOOL_THREAD_NUMS = Integer.getInteger("nettyrpc-default-thread-nums", 16);
    public static final int SYSTEM_PROPERTY_THREADPOOL_QUEUE_NUMS = Integer.getInteger("nettyrpc-default-queue-nums", 1);
    public static final int PARALLEL = Math.max(2, Runtime.getRuntime().availableProcessors());
    public static final String DELIMITER = ":";
}
