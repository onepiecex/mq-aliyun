package com.github.mq.aliyun.consumer;

/**
 * Created by wangziqing on 17/7/13.
 */
public interface  MqConsumer{
    String BROADCASTING = "BROADCASTING";
    String CLUSTERING = "CLUSTERING";

    void init(Ons ons);
}
