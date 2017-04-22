package com.rpc.framework.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by liujiawei on 2017/4/22.
 */
public class ServiceEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public ServiceEvent(Object source) {
        super(source);
    }
}
