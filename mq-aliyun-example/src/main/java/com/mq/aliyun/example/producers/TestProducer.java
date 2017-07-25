package com.mq.aliyun.example.producers;

import com.github.mq.producer.models.Pid;
import com.github.mq.producer.models.To;

/**
 * Created by wangziqing on 17/7/25.
 */
@Pid("PID_TEST")
public enum  TestProducer {
    @To(topic = "TEST", tag = "dish.add")
    DISH_ADD,

    @To(topic = "TEST", tag = "dish.update")
    DISH_UPDATE,

    @To(topic = "TEST", tag = "dish.del")
    DISH_DEL
}
