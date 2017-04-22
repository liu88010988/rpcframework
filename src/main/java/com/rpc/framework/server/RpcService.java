package com.rpc.framework.server;

import com.rpc.framework.event.ServiceEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by liujiawei on 2017/4/22.
 * 对应nettyrpc:service 解析出来的bean
 */
public class RpcService implements ApplicationContextAware, ApplicationListener {
    private String interfaceName;
    private String ref;
    private ApplicationContext applicationContext;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        applicationContext.publishEvent(new ServiceEvent(new Object()));
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        ServerExecutor executor = ServerExecutor.getInstance();
        executor.getServerMap().put(interfaceName, applicationContext.getBean(ref));
    }
}
