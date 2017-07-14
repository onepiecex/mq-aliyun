package com.github.mq.aliyun.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.github.mq.aliyun.models.ConsumerId;
import com.github.mq.aliyun.models.ConsumerOptional;

import java.util.Properties;

/**
 * Created by wangziqing on 17/7/13.
 */
public class ConsumerRun extends Thread {

    private final String accessKey;
    private final String secretKey;
    private final String suffix;
    private final ConsumerId consumerId;

    public ConsumerRun(String accessKey, String secretKey, String suffix,ConsumerId consumerId){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.suffix = suffix;
        this.consumerId = consumerId;
    }


    @Override
    public void run() {
        ConsumerOptional consumerOptional = consumerId.getConsumerOptional();

        String cid = consumerId.getCid() + suffix;
        String topic = consumerId.getTopic() + suffix;

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, cid);
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.ConsumeThreadNums, consumerOptional.getConsumeThread());
        properties.put(PropertyKeyConst.MessageModel, consumerOptional.getConsumerModel());
        properties.put(PropertyKeyConst.MaxReconsumeTimes, consumerOptional.getMaxReconsume());

        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(topic, consumerId.getTags(), (message, context) -> {
            return Action.CommitMessage;
        });
    }


}
