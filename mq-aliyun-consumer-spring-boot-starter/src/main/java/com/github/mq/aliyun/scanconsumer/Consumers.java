package com.github.mq.aliyun.scanconsumer;

import com.github.mq.aliyun.consumer.MqConsumer;
import com.github.mq.aliyun.consumer.Ons;
import com.github.mq.aliyun.events.TestConsumers;

/**
 * Created by wangziqing on 17/7/13.
 */
public class Consumers extends MqConsumer {

    private static final String topic = "topic";
    @Override
    public void init(Ons ons) {
        ons.defaultTopic("MEICANYUN");

        //创建订单
        ons.consumer("MEICANYUN_DISH_CID_1").subscribeTag("dish.create||order.del", TestConsumers::orderCreate);
        //修改订单
        ons.consumer("MEICANYUN_DISH_CID_1").subscribeTag("dish.update", TestConsumers::orderCreate);
        //删除订单
        ons.consumer("MEICANYUN_DISH_CID_1").subscribeTag("dish.delete", TestConsumers::orderCreate);


        ons.consumer("MEICANYUN_DISH_CID_2")
                .subscribeTopic("MEICANYUN_EXPRESS")
                //创建订单
                .subscribeTag("dish.create||order.del", TestConsumers::orderCreate)
                //修改订单
                .subscribeTag("dish.update", TestConsumers::orderCreate)
                //删除订单
                .subscribeTag("dish.delete", TestConsumers::orderCreate);

    }
}