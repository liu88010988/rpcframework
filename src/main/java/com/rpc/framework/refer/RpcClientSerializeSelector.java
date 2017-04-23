/**
 * Copyright (C) 2016 Newland Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rpc.framework.refer;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.rpc.framework.refer.jdk.JdkClientHandler;
import com.rpc.framework.serialize.RpcSerializeSelector;
import com.rpc.framework.serialize.SerializeProtocol;
import com.rpc.framework.server.RpcServerHandler;
import com.rpc.framework.server.jdk.JdkServerHandler;
import io.netty.channel.ChannelPipeline;

import java.util.Map;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class RpcClientSerializeSelector implements RpcSerializeSelector {

    private static ClassToInstanceMap<RpcClientHandler> handler = MutableClassToInstanceMap.create();

    static {
        handler.putInstance(JdkClientHandler.class, new JdkClientHandler());
    }

    public void select(SerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol) {
            case JDKSERIALIZE: {
                handler.getInstance(JdkClientHandler.class).handle(pipeline);
                break;
            }
        }
    }
}
