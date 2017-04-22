package com.rpc.framework.namespace;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcServiceParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return RpcService.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String interfaceName = element.getAttribute("interfaceName");
        builder.addPropertyValue("interfaceName", interfaceName);
        String ref = element.getAttribute("ref");
        builder.addPropertyValue("ref", ref);
    }
}

