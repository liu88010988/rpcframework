package com.rpc.framework.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcRegisteryParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String ipAddr = element.getAttribute("ipAddr");
        String protocol = element.getAttribute("protocol");
        String id = element.getAttribute("id");
        RootBeanDefinition definition = new RootBeanDefinition();
        definition.setBeanClass(RpcRegistery.class);
        definition.setLazyInit(false);
        definition.getPropertyValues().add("ipAddr", ipAddr);
        definition.getPropertyValues().add("protocol", protocol);
        parserContext.getRegistry().registerBeanDefinition(id, definition);
        return definition;
    }
}


