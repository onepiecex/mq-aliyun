package com.github.mq.consumer;

/**
 * Created by wangziqing on 17/7/13.
 */
public interface ConsumerAble {
    String BROADCASTING = "BROADCASTING";
    String CLUSTERING = "CLUSTERING";

    void init(Ons ons);
}

