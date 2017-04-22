package com.rpc.framework.refer;

import com.google.common.eventbus.EventBus;

/**
 * Created by liujiawei on 2017/4/22.
 * 对应nettyrpc:reference 解析出来的bean
 */
public class RpcReference {
    private String interfaceName;
    private String ipAddr;
    private String protocol;
    private EventBus eventBus = new EventBus();

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


}
