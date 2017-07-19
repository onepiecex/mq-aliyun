package com.github.mq.producer.springboot.impls;

import com.aliyun.openservices.ons.api.*;
import com.github.mq.producer.springboot.ProducerAble;
import com.github.mq.producer.springboot.ProducerFactory;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.github.mq.core.scan.constant.MqConstant;
import com.github.mq.producer.springboot.ProducerSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Properties;

/**
 * Created by wangziqing on 17/7/19.
 */
public class ProducerFactoryImpl implements ProducerFactory {
    private final static Logger logger = LoggerFactory.getLogger(ProducerFactoryImpl.class);

    public static final String MQ_PRODUCER_SEND_MSG_TIMEOUT = "aliyun.mq.producer.sendTimeOut";
    private static volatile Map<String, com.aliyun.openservices.ons.api.Producer> producerMap = Maps.newConcurrentMap();

    private String suffix;
    private String accessKey;
    private String secretKey;
    private String sendTimeOut = "3000";

    @Autowired
    private Environment env;

    private ProducerSerialize producerSerialize;

    @Override
    public void send(ProducerAble producerAble, Object message) {
        send(producerAble, message, null, null, null);
    }

    @Override
    public void send(ProducerAble producerAble, Object message, SendCallback sendCallback) {
        send(producerAble, message, null, null, sendCallback);
    }

    @Override
    public void send(ProducerAble producerAble, Object message, String key) {
        send(producerAble, message, key, null, null);
    }

    @Override
    public void send(ProducerAble producerAble, Object message, String key, SendCallback sendCallback) {
        send(producerAble, message, key, null, sendCallback);
    }

    @Override
    public void send(ProducerAble producerAble, Object message, Long deliverTime) {
        send(producerAble, message, null, deliverTime, null);
    }

    @Override
    public void send(ProducerAble producerAble, Object message, Long deliverTime, SendCallback sendCallback) {
        send(producerAble, message, null, deliverTime, sendCallback);
    }

    @Override
    public void send(ProducerAble producerAble, Object message, String key, Long deliverTime) {
        send(producerAble, message, key, deliverTime, null);
    }

    @Override
    public void send(ProducerAble producerAble, Object message, String key, Long deliverTime, SendCallback sendCallback) {
        Message msg = new Message(
                producerAble.getTopic() + suffix,
                // 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
                producerAble.getTag() + suffix,
                // Message Body
                // 任何二进制形式的数据， ONS不做任何干预，
                // 需要Producer与Consumer协商好一致的序列化和反序列化方式
                producerSerialize.objToBytes(message));

        if (!Strings.isNullOrEmpty(key)) {
            msg.setKey(key);
        }

        if (null != deliverTime && 0 != deliverTime)
            msg.setStartDeliverTime(deliverTime);

        send(producerAble.getPid(), msg, sendCallback);
    }

    private void send(String pid, Message message, SendCallback sendCallback) {
        if (null == message) {
            throw new RuntimeException("消息内容不能为空");
        }
        if (null == pid || pid.isEmpty()) {
            throw new RuntimeException("PID不能为空");
        }
        Producer producer = get(pid);
        if (!producer.isStarted()) {
            producer.start();
        }
        if (null != sendCallback) {
            producer.sendAsync(message, sendCallback);
        } else {
            producer.sendOneway(message);
        }
    }

    private boolean isInit = false;

    public void init(ProducerSerialize producerSerialize) {
        if (!isInit) {
            isInit = true;
            this.producerSerialize = producerSerialize;
            accessKey = env.getProperty(MqConstant.ACCESS_KEY);
            if (null == accessKey || accessKey.trim().isEmpty()) {
                throw new RuntimeException(String.format("mq 启动失败, %s is require", MqConstant.ACCESS_KEY));
            }

            secretKey = env.getProperty(MqConstant.SECRET_KEY);
            if (null == secretKey || secretKey.trim().isEmpty()) {
                throw new RuntimeException(String.format("mq 启动失败, %s is require", MqConstant.SECRET_KEY));
            }

            suffix = env.getProperty(MqConstant.MQ_SUFFIX);
            if (null == suffix || suffix.trim().isEmpty()) {
                throw new RuntimeException(String.format("mq 启动失败, %s is require", MqConstant.MQ_SUFFIX));
            }
            String timeout = env.getProperty(MQ_PRODUCER_SEND_MSG_TIMEOUT);
            if (null != timeout && !timeout.isEmpty()) {
                sendTimeOut = timeout;
            }
        }
    }

    @Override
    public Producer get(String pid) {
        Producer producer;
        if (null == (producer = producerMap.get(pid))) {
            String pidsuffix = pid + suffix;
            Properties properties = new Properties();
            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.AccessKey, accessKey);
            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.SecretKey, secretKey);
            //您在控制台创建的Producer ID
            properties.put(PropertyKeyConst.ProducerId, pidsuffix);
            //设置发送超时时间，单位毫秒
            properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, sendTimeOut);

            producer = ONSFactory.createProducer(properties);
            producerMap.put(pid, producer);
            // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
            producer.start();
            logger.info(String.format("生产者启动成功: %s", pidsuffix));
        }
        return producer;
    }
}
