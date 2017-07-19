package com.github.mq.consumer;

import com.github.mq.consumer.util.ControllerMethods;
import com.github.mq.consumer.util.WithControllerMethod;

/**
 * Created by wangziqing on 17/7/13.
 */
public interface ConsumerBuild extends WithControllerMethod<ConsumerBuild> {
    ConsumerBuild subscribeTopic(String topic);
    ConsumerBuild subscribeTag(String tag, ControllerMethods.ControllerMethod controllerMethod);
}
