package com.mq.aliyun.example.producers;

import com.github.mq.producer.models.Pid;
import com.github.mq.producer.models.To;

/**
 * Created by wangziqing on 17/7/26.
 */
@Pid(value = "PID_MEICANYUN" , ordered = true)
public enum  OrderProducer {
    @To(topic = "MEICANYUN_SHARDING",tag = "send.mail")
    SEND_MAIL;
}
