package com.github.mq.aliyun;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * Created by wangziqing on 17/7/12.
 */

@SpringBootApplication
public class Application{


    public static void main(String[] args) throws NoSuchMethodException {
        SpringApplication.run(Application.class);
        DefaultListableBeanFactory w;

        getProducer().sendOneway(new Message("WEBSOCKET_dev","test", JSONObject.toJSONBytes("hello")));
    }


    public static Producer getProducer() {
        String pid = "PID_WEBSOCKET_dev";
        String accessKey = "accessKey";
        String secretKey = "secretKey";


        Properties properties = new Properties();
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        //您在控制台创建的Producer ID
        properties.put(PropertyKeyConst.ProducerId, pid);
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "1000");

        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();
        return producer;
    }



}

