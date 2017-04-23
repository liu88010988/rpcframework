package com.rpc.framework.serialize;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by liujiawei on 2017/4/23.
 */
public interface MessageUtil {
    int MESSAGE_LENGTH = 4;

    void encode(final ByteBuf out, final Object obj) throws IOException;

    Object decode(byte[] bytes) throws IOException;
}
