package com.rpc.framework.namespace;

import com.rpc.framework.serialize.SerializeProtocol;
import com.rpc.framework.server.RpcServerExecutor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by liujiawei on 2017/4/22.
 * 对应nettyrpc:reference 解析出来的bean
 */
public class RpcRegistery implements InitializingBean, DisposableBean {
    private String ipAddr;
    private String protocol;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public void destroy() throws Exception {
        RpcServerExecutor.getInstance().stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RpcServerExecutor executor = RpcServerExecutor.getInstance();
        executor.setServerAddress(ipAddr);
        executor.setSerializeProtocol(Enum.valueOf(SerializeProtocol.class, protocol));
        executor.start();
    }
}


