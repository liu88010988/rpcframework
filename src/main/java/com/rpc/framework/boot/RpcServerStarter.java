package com.rpc.framework.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcServerStarter {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:rpc-config.xml");
    }
}
