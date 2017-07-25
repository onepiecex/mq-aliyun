package com.mq.aliyun.example;

import com.github.mq.consumer.ConsumerAble;
import com.github.mq.consumer.Ons;
import com.mq.aliyun.example.consumers.TestConsumer;

/**
 * Created by wangziqing on 17/7/25.
 */

public class Consumers implements ConsumerAble {

    @Override
    public void init(Ons ons) {
        ons.consumer("CID_TEST_DISH")
                .subscribeTopic("TEST")
                .subscribeTag("dish.add || dish.update",TestConsumer::dishAdd)
                .subscribeTag("dish.del",TestConsumer::dishDel);

    }
}
