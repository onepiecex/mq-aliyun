package com.github.mq.aliyun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wangziqing on 17/7/12.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
//        ConfigurableApplicationContext c = SpringApplication.run(Application.class);
//        c.addApplicationListener(new ReadyEvent());
    }

//    public void test(Ons ons){
//
//
//
////
////        ons.com.github.mq.aliyun.util.consumer("MEICANYUN_ORDER_CID").subscribe("topic","tag", TestConsumers::orderCreate)
////                .subscribeTop("topic")
////                .subscribeTag(,"tag", TestConsumers::orderCreate)
//
//    }
//
//    @Bean
//    public Test getTest(){
//        return new Test("test");
//    }
}
