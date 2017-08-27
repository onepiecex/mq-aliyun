package com.mq.aliyun.example.producers;

import com.github.mq.producer.models.Pid;
import com.github.mq.producer.models.To;

/**
 * Created by wangziqing on 17/7/25.
 */
@Pid(value = "PID_MEICANYUN")
public enum  TestProducer {
    @To(topic = "MEICANYUN", tag = "dish.add")
    DISH_ADD,

    @To(topic = "WEBSOCKET", tag = "dish.update")
    DISH_UPDATE,

    @To(topic = "WEBSOCKET", tag = "dish.del")
    DISH_DEL
}
