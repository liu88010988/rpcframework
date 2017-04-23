package com.rpc.framework.server;

import com.rpc.framework.model.MessageRequest;
import com.rpc.framework.model.MessageResponse;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by liujiawei on 2017/4/23.
 */
public class RpcServerTask implements Callable<Boolean> {
    private MessageRequest request;
    private MessageResponse response;
    private Map<String, Object> serverMap;

    public RpcServerTask(MessageRequest request, MessageResponse response, Map<String, Object> serverMap) {
        this.request = request;
        this.response = response;
        this.serverMap = serverMap;
    }

    @Override
    public Boolean call() {
        response.setMessageId(request.getMessageId());
        try {
            response.setResult(getResult());
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
            return Boolean.FALSE;
        }
    }

    private Object getResult() throws Exception {
        String clasName = request.getClassName();
        Object serverBean = serverMap.get(clasName);
        String methodName = request.getMethodName();
        Object[] args = request.getArgs();
        return MethodUtils.invokeExactMethod(serverBean, methodName, args);
    }
}
