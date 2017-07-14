package com.github.mq.aliyun.consumer;

/**
 * Created by wangziqing on 17/7/13.
 */
public abstract class MqConsumer {
    public static final String BROADCASTING = "BROADCASTING";
    public static final String CLUSTERING = "CLUSTERING";

    public abstract void init(Ons ons);
}
