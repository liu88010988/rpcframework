package com.rpc.framework.model;

import java.io.Serializable;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class MessageRequest implements Serializable {
    private static final long serialVersionUID = 6340013690931822345L;
    private String messageId;
    private String className;
    private String methodName;
    private Class[] argTypes;
    private Object[] args;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(Class[] argTypes) {
        this.argTypes = argTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
