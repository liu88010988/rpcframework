package com.rpc.framework.refer;

import com.rpc.framework.config.RpcConfig;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcCallBack {
    private Object result;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public Object start() {
        lock.lock();
        try {
            if (result == null) {
                condition.await(RpcConfig.SYSTEM_PROPERTY_MESSAGE_CALLBACK_TIMEOUT, TimeUnit.MILLISECONDS);
            }
            return result;
        } catch (InterruptedException e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void over(Object obj) {
        lock.lock();
        try {
            this.result = obj;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
