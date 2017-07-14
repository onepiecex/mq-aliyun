package com.github.mq.aliyun.consumer.impls;

import com.github.mq.aliyun.consumer.ConsumerBuild;
import com.github.mq.aliyun.consumer.ConsumerTopicBuild;
import com.github.mq.aliyun.models.ConsumerId;

/**
 * Created by wangziqing on 17/7/13.
 */
public class ConsumerTopicBuildImpl implements ConsumerTopicBuild{

    private final ConsumerId consumerId;

    public ConsumerTopicBuildImpl(final ConsumerId consumerId){
        this.consumerId = consumerId;
    }
    @Override
    public ConsumerBuild subscribeTopic(String topic) {
        if(null == topic || topic.trim().isEmpty()){
            throw new RuntimeException(String.format("%s 订阅的topic不能为空", consumerId.getCid()));
        }
        if(null != consumerId.getTopic()){
            if(!consumerId.getTopic().equals(topic)){
                throw new RuntimeException(String.format("同一个JVM下相同的CID : %s 只能对应相同的TOPIC : %s",
                        consumerId.getCid(), consumerId.getTopic()));
            }
        }
        consumerId.setTopic(topic);
        return new ConsumerBuildImpl(consumerId);
    }
}
