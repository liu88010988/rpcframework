package com.rpc.framework.model;

import java.io.Serializable;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class MessageResponse implements Serializable {
    private static final long serialVersionUID = -5716520441922082986L;
    private String messageId;
    private Object result;
    private Object error;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
