package com.github.mq.aliyun.scanconsumer;

import com.github.mq.aliyun.consumer.MqConsumer;
import com.github.mq.aliyun.consumer.Ons;
import com.github.mq.aliyun.events.TestConsumers;
import org.springframework.stereotype.Component;

/**
 * Created by wangziqing on 17/7/13.
 */
@Component
public class Consumers2 extends MqConsumer {

    private static final String topic = "topic";
    @Override
    public void init(Ons ons) {
        ons.consumer("MEICANYUN_ORDER_CID_1")
                .subscribeTopic(topic)
                .with("order.create||order.del", TestConsumers::orderCreate)
                .with("order.update", TestConsumers::orderCreate)
                .with("order.delete", TestConsumers::orderCreate);

        ons.consumer("MEICANYUN_ORDER_CID_2")
                .subscribeTopic(topic)
                .with("order.create", TestConsumers::orderCreate);


        ons.consumer("MEICANYUN_ORDER_CID_3")
                .subscribeTopic(topic)
                .with("order.create", TestConsumers::orderCreate);
    }
}
