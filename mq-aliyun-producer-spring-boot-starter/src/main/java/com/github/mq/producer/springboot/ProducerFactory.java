package com.github.mq.producer.springboot;

import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;

/**
 * Created by wangziqing on 17/7/17.
 */
public interface ProducerFactory {

    void send(ProducerAble producerAble, Object message);

    void send(ProducerAble producerAble, Object message, SendCallback sendCallback);

    void send(ProducerAble producerAble, Object message, String key);

    void send(ProducerAble producerAble, Object message, String key, SendCallback sendCallback);

    void send(ProducerAble producerAble, Object message, Long deliverTime);

    void send(ProducerAble producerAble, Object message, Long deliverTime, SendCallback sendCallback);

    void send(ProducerAble producerAble, Object message, String key, Long deliverTime);

    void send(ProducerAble producerAble, Object message, String key, Long deliverTime, SendCallback sendCallback);

    Producer get(String pid);
}
