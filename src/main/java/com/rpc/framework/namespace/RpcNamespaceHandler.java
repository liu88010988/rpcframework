package com.rpc.framework.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new RpcRegisteryParser());
        registerBeanDefinitionParser("service", new RpcServiceParser());
        registerBeanDefinitionParser("reference", new RpcReferenceParser());
    }
}
