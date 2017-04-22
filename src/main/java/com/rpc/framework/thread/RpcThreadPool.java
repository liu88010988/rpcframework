package com.rpc.framework.thread;

import com.rpc.framework.config.RpcConfig;

import java.util.concurrent.*;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcThreadPool {

    public static ExecutorService getExecutor(int threasNum, int queueSize) {
        return new ThreadPoolExecutor(threasNum, threasNum, 0, TimeUnit.MILLISECONDS, createQueue(queueSize), new RpcThreadFactory("rpc-thread-executor",true));
    }

    private static BlockingQueue<Runnable> createQueue(int size) {
        BlockingQueueType type = BlockingQueueType.fromString(RpcConfig.SYSTEM_PROPERTY_THREADPOOL_QUEUE_NAME_ATTR);
        switch (type) {
            case SYNCHRONOUS_QUEUE:
                return new SynchronousQueue<Runnable>();
            case ARRAY_BLOCKING_QUEUE:
                return new ArrayBlockingQueue<Runnable>(size);
            case LINKED_BLOCKING_QUEUE:
                return new LinkedBlockingDeque<Runnable>(size);
        }
        return null;
    }
}
