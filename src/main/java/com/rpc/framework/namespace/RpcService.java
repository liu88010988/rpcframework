package com.rpc.framework.namespace;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcService {
    private String interfaceName;
    private String ref;

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

}
