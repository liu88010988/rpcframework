package com.rpc.framework.refer;

import com.google.common.eventbus.EventBus;
import com.rpc.framework.event.ClientEvent;
import com.rpc.framework.event.ClientListener;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by liujiawei on 2017/4/22.
 * 对应nettyrpc:reference 解析出来的bean
 */
public class RpcReference implements FactoryBean, InitializingBean, DisposableBean {
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


    @Override
    public void destroy() throws Exception {
        eventBus.post(new ClientEvent());
    }

    @Override
    public Object getObject() throws Exception {
        return RpcClientExecutor.getInstance().execute(getObjectType());
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return getClass().getClassLoader().loadClass(interfaceName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RpcClientExecutor.getInstance().start(ipAddr, protocol);
        eventBus.register(new ClientListener());
    }
}
