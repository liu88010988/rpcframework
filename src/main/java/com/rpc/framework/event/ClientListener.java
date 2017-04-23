package com.rpc.framework.event;

import com.google.common.eventbus.Subscribe;
import com.rpc.framework.refer.RpcClientExecutor;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class ClientListener {

    @Subscribe
    public void listen(ClientEvent event) {
        RpcClientExecutor.getInstance().stop();
    }
}
