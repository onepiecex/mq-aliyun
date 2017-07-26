package com.mq.aliyun.example;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.github.mq.producer.ProducerFactory;
import com.github.mq.producer.models.DeliveryOption;
import com.mq.aliyun.example.model.Dish;
import com.mq.aliyun.example.producers.OrderProducer;
import com.mq.aliyun.example.producers.TestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wangziqing on 17/7/25.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private ProducerFactory producerFactory;


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        producerFactory.sendAsync(TestProducer.DISH_ADD, new Dish());

        Dish dish = new Dish();
        dish.setId(2L);
        dish.setName("www");
        producerFactory.sendAsync(TestProducer.DISH_UPDATE, dish,
                new DeliveryOption("key").setDeliverTime(System.currentTimeMillis() + 1000 * 60));

        producerFactory.sendAsync(TestProducer.DISH_DEL, 1L, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
                //send success
                //do some thing...
            }

            @Override
            public void onException(OnExceptionContext context) {
                System.out.println(context);
                //send fail
                //do some thing...
            }
        });

        SendResult sendResult = producerFactory.orderSend(OrderProducer.SEND_MAIL,"message","shardingKey");
    }


}
