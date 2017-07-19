package com.github.mq.consumer;

import com.github.mq.consumer.models.ConsumerOptional;

/**
 * Created by wangziqing on 17/7/13.
 */
public interface Ons {
    void defaultTopic(String topic);
    ConsumerBuild consumer(String cid);
    ConsumerBuild consumer(String cid, ConsumerOptional consumerOptional);

    ConsumerBuild consumerOrdered(String cid);
    ConsumerBuild consumerOrdered(String cid, ConsumerOptional consumerOptional);
}
