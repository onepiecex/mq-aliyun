package com.github.mq.consumer.impls;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.github.mq.consumer.models.*;
import com.github.mq.consumer.parms.ArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by wangziqing on 17/7/13.
 */
public class ConsumerRun extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerRun.class);

    private final String accessKey;
    private final String secretKey;
    private final String suffix;
    private final ConsumerId consumerId;

    public ConsumerRun(String accessKey, String secretKey, String suffix, ConsumerId consumerId) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.suffix = suffix;
        this.consumerId = consumerId;
    }


    @Override
    public void run() {
        ConsumerOptional consumerOptional = consumerId.getConsumerOptional();

        String cid = consumerId.getCid() + suffix;

        String topic = consumerId.getTopic();
        if (null == topic) {
            throw new RuntimeException(String.format("%s 必须订阅一个topic", consumerId.getCid()));
        }
        topic += suffix;

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, cid);
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.ConsumeThreadNums, consumerOptional.getConsumeThread());
        properties.put(PropertyKeyConst.MessageModel, consumerOptional.getConsumerModel());
        properties.put(PropertyKeyConst.MaxReconsumeTimes, consumerOptional.getMaxReconsume());
        properties.put(PropertyKeyConst.SuspendTimeMillis,consumerOptional.getSuspendTime());
        String tags = consumerId.getTags();

        if(tags.contains("||")){
            tags = tags.substring(0,tags.length()-2);
        }
        logger.info("发现频道 CID: {}  订阅TOPIC: {}  TAG: {}", cid, topic, tags);

        if (consumerId.isOrdered()) {
            properties.put(PropertyKeyConst.SuspendTimeMillis, consumerOptional.getSuspendTime());
            OrderConsumer orderConsumer = ONSFactory.createOrderedConsumer(properties);

            orderConsumer.subscribe(topic,tags,((message, context) ->
                    dispatch(consumerId, message, consumerOptional.getMaxReconsume()).equals(Action.CommitMessage)?
                            OrderAction.Success : OrderAction.Suspend
            ));
            orderConsumer.start();
        } else {
            Consumer consumer = ONSFactory.createConsumer(properties);
            consumer.subscribe(topic, tags, (message, context) ->
                    dispatch(consumerId, message, consumerOptional.getMaxReconsume()).equals(Action.CommitMessage)?
                            com.aliyun.openservices.ons.api.Action.CommitMessage : com.aliyun.openservices.ons.api.Action.ReconsumeLater
            );
            consumer.start();
       }
        logger.info("消费者启动成功 -->  : CID: {}({}) , 消费模式: {} , 消费线程数: {} , 最大重试次数: {} {} , 订阅 TOPIC: {} TAG: {}",
                cid, consumerId.isOrdered()?"有序":"无序",
                consumerOptional.getConsumerModel(), consumerOptional.getConsumeThread(), consumerOptional.getMaxReconsume(),
                consumerId.isOrdered()?", 重试前的等待时间:"+consumerOptional.getSuspendTime()+"毫秒":"",topic,tags);
    }


    private static Action dispatch(ConsumerId consumerId, Message message, int defaultReconsume) {
        Tag tag = consumerId.getTagMap().get(message.getTag());
        if (null == tag) {
            throw new RuntimeException(String.format("%s 没有注册 请检查MqConsumer的init配置", message.getTag()));
        }
        int reconsume = tag.getReconsume() == null ? defaultReconsume : tag.getReconsume();
        if (message.getReconsumeTimes() > reconsume) {
            logger.warn("超过允许的最大重试次数。 允许的重试次数: {}, 当前重试次数: {}", reconsume, message.getReconsumeTimes()) ;
            return Action.CommitMessage;
        }
        ArgumentExtractor[] extractors = tag.getArgumentExtractors();
        Object[] parms = new Object[extractors.length];

        for (int i = 0;  i< extractors.length; i++) {
            ArgumentExtractor extractor = extractors[i];
            if (null != extractor) {
                Result result = extractor.extract(message);
                if (null != result.getAction()) {
                    return result.getAction();
                }
                parms[i] = result.getResult();
            }
        }

        Object action = null;
        try {
            action = tag.invoke(parms);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (null == action) {
            return Action.ReconsumeLater;
        }
        return (Action) action;
    }
}
