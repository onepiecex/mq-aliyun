package com.github.mq;

import com.github.mq.consumer.ConsumerAble;
import com.github.mq.consumer.Ons;
import com.github.mq.consumer.models.ConsumerOptional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wangziqing on 17/7/20.
 */
@SpringBootApplication
public class App implements ConsumerAble{
    @Override
    public void init(Ons ons) {
        ons.defaultTopic("ww");
        ons.consumer("CID").subscribeTag("tag1",TestConsumer::test);
        ons.consumer("CID").subscribeTag("tag2",TestConsumer::test);

        ons.consumerOrdered("CID2",new ConsumerOptional(10,30)).subscribeTag("tag_a || tab_c",TestConsumer::test);
        ons.consumer("CID2").subscribeTag("tag_b",TestConsumer::test);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
