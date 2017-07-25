package com.mq.aliyun.example;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.github.mq.producer.ProducerFactory;
import com.github.mq.producer.models.DeliveryOption;
import com.mq.aliyun.example.model.Dish;
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
        producerFactory.sendAsync(TestProducer.DISH_ADD, new Dish(1L, "name"));

        producerFactory.sendAsync(TestProducer.DISH_UPDATE,
                new Dish(2L, "name"),
                new DeliveryOption("key").setDeliverTime(System.currentTimeMillis() + 1000 * 60));

        producerFactory.sendAsync(TestProducer.DISH_DEL, 1L, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                //send success
                //do some thing...
            }

            @Override
            public void onException(OnExceptionContext context) {
                //send fail
                //do some thing...
            }
        });
    }


}
