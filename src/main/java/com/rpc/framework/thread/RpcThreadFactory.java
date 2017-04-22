package com.rpc.framework.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcThreadFactory implements ThreadFactory {
    private AtomicInteger threadNum = new AtomicInteger(1);
    private String prefix;
    private boolean daemon;
    private ThreadGroup threadGroup;

    public RpcThreadFactory(String prefix) {
        this(prefix, false);
    }

    public RpcThreadFactory(String prefix, boolean daemon) {
        this.prefix = prefix;
        this.daemon = daemon;
        this.threadGroup = Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + "-thread-" + threadNum.getAndIncrement();
        Thread thread = new Thread(threadGroup, r, name);
        thread.setDaemon(daemon);
        return thread;
    }
}
