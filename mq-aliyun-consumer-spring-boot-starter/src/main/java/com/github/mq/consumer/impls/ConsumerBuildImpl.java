package com.github.mq.consumer.impls;

import com.github.mq.consumer.ConsumerBuild;
import com.github.mq.consumer.models.ConsumerId;
import com.github.mq.consumer.util.ControllerMethods;
import com.github.mq.consumer.util.LambdaRoute;

/**
 * Created by wangziqing on 17/7/13.
 */
public class ConsumerBuildImpl implements ConsumerBuild {

    private final ConsumerId consumerId;

    public ConsumerBuildImpl(final ConsumerId consumerId){
        this.consumerId = consumerId;
    }
    @Override
    public ConsumerBuild subscribeTag(String tag, ControllerMethods.ControllerMethod controllerMethod) {
        LambdaRoute lambdaRoute = LambdaRoute.resolve(controllerMethod);
        consumerId.addTag(tag,lambdaRoute.getFunctionalMethod());
        return this;
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
        return  this;
    }
}
