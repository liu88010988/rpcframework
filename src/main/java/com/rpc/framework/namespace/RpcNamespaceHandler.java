package com.rpc.framework.namespace;

import com.rpc.framework.refer.RpcReferenceParser;
import com.rpc.framework.server.RpcServiceParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by liujiawei on 2017/4/22.
 * 解析自定义的xml标签
 */
public class RpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new RpcRegisteryParser());
        registerBeanDefinitionParser("service", new RpcServiceParser());
        registerBeanDefinitionParser("reference", new RpcReferenceParser());
    }
}
