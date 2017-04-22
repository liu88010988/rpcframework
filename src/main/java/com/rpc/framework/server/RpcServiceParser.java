package com.rpc.framework.server;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by liujiawei on 2017/4/22.
 * 解析自定义的 nettyrpc:service标签，并将bean注册到spring容器中
 */
public class RpcServiceParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String interfaceName = element.getAttribute("interfaceName");
        String ref = element.getAttribute("ref");
        String id = element.getAttribute("id");
        RootBeanDefinition definition = new RootBeanDefinition();
        definition.setBeanClass(RpcService.class);
        definition.setLazyInit(false);
        definition.getPropertyValues().add("interfaceName", interfaceName);
        definition.getPropertyValues().add("ref", ref);
        parserContext.getRegistry().registerBeanDefinition(id, definition);
        return definition;
    }
}

