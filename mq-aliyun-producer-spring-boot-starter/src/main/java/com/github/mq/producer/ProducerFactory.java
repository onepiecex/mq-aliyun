package com.github.mq.producer;

import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.github.mq.producer.models.DeliveryOption;

/**
 * Created by wangziqing on 17/7/17.
 */
public interface ProducerFactory {
    void sendAsync(final Enum address,final Object message);
    void sendAsync(final Enum address,final Object message, final DeliveryOption deliveryOption);
    void sendAsync(final Enum address,final Object message, final SendCallback sendCallback);
    void sendAsync(final Enum address, final Object message, final DeliveryOption deliveryOption, final SendCallback sendCallback);

    SendResult orderSend(final Enum address, final Object message, final String shardingKey);
    SendResult orderSend(final Enum address, final Object message, final String shardingKey,final DeliveryOption deliveryOption);

    Producer getProducer(String pid);
    OrderProducer getOrderProducer(String pid);
    void addProducer(String pid);
    void addOrdereProducer(String pid);
}
