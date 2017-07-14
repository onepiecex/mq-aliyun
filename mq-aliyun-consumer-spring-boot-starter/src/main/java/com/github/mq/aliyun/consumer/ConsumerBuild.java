package com.github.mq.aliyun.consumer;

import com.github.mq.aliyun.util.ControllerMethods;
import com.github.mq.aliyun.util.WithControllerMethod;

/**
 * Created by wangziqing on 17/7/13.
 */
public interface ConsumerBuild extends WithControllerMethod<ConsumerBuild> {
    ConsumerBuild subscribeTopic(String topic);
    ConsumerBuild subscribeTag(String tag,ControllerMethods.ControllerMethod controllerMethod);
}
