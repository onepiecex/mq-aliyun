package com.github.mq.aliyun.consumer.impls;

import com.github.mq.aliyun.consumer.*;
import com.github.mq.aliyun.models.ConsumerId;
import com.github.mq.aliyun.models.ConsumerOptional;
import com.github.mq.aliyun.models.Tag;
import com.mq.aliyun.core.scan.ClassScanner;
import com.mq.aliyun.core.scan.constant.MqConstant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangziqing on 17/7/13.
 */
public class OnsImpl implements Ons {
    public static final String MQ_CONSUMER_DEFAULT_THREAD = "aliyun.mq.consumer.defaultThread";
    public static final String MQ_CONSUMER_DEFAULT_MODEL = "aliyun.mq.consumer.defaultModel";

    public static final String MQ_CONSUMER_DEFAULT_MAX_RECONSUME = "aliyun.mq.consumer.defaultMaxReconsume";

    private static int defaultConsumerThread = 20;
    private static String defaultConsumerModel = MqConsumer.CLUSTERING;
    private static int defaultMaxReconsume = 16;

    private String defaultTopic;

    private String suffix;
    private String accessKey;
    private String secretKey;
    private Map<String, ConsumerId> consumerMap = new HashMap<>();
    private final DefaultListableBeanFactory beanFactory;

    public OnsImpl(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        init();
    }

    public void start() {
        ClassScanner.scan(MqConsumer.class).forEach((Class<MqConsumer> mqConsumerClass) -> {
            MqConsumer consumer = beanFactory.createBean(mqConsumerClass);
            consumer.init(this);
        });
        consumerMap.forEach((cid, consumerId) -> {
            Map<String, Tag> tagMap = consumerId.getTagMap();
            for (Map.Entry<String, Tag> tagEntry : tagMap.entrySet()) {
                Tag tag = tagEntry.getValue();
                Object invokeObj;
                try {
                    invokeObj = beanFactory.getBean(tag.getInvokeCls().getName());
                } catch (BeansException b) {
                    BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(tag.getInvokeCls().getName());
                    beanFactory.registerBeanDefinition(tag.getInvokeCls().getName(), beanDefinition.getBeanDefinition());
                    invokeObj = beanFactory.getBean(tag.getInvokeCls().getName());
                }
                tagEntry.getValue().setInvokeObject(invokeObj);
            }
            new ConsumerRun(accessKey, secretKey, suffix, consumerId).start();
        });
    }

    private void init() {
        Environment env = beanFactory.getBean(Environment.class);

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


        String consumerThread = env.getProperty(MQ_CONSUMER_DEFAULT_THREAD);
        String consumerModel = env.getProperty(MQ_CONSUMER_DEFAULT_MODEL);
        String consumerMaxReconsume = env.getProperty(MQ_CONSUMER_DEFAULT_MAX_RECONSUME);

        if (null != consumerThread) {
            defaultConsumerThread = Integer.valueOf(defaultConsumerThread);
        }
        if(null != consumerMaxReconsume){
            defaultMaxReconsume = Integer.valueOf(consumerMaxReconsume);
        }
        if (null != consumerModel && !consumerModel.trim().isEmpty()) {
            defaultConsumerModel = consumerModel;
        }
    }

    @Override
    public void defaultTopic(String topic) {
        this.defaultTopic = topic;
    }

    @Override
    public ConsumerBuild consumer(String cid) {
        return consumer(cid, new ConsumerOptional()
                .setConsumerModel(defaultConsumerModel)
                .setConsumeThread(defaultConsumerThread)
                .setMaxReconsume(defaultMaxReconsume));
    }

    public ConsumerBuild consumer(String cid, ConsumerOptional consumerOptional) {
        ConsumerId consumerId = consumerMap.get(cid);
        if (null == consumerId) {
            consumerId = new ConsumerId(cid, consumerOptional);
            consumerMap.put(cid, consumerId);
        }
        if(null != defaultTopic){
            consumerId.setTopic(defaultTopic);
        }
        return new ConsumerBuildImpl(consumerId);

    }
//
//    @Override
//    public ConsumerBuild consumer(String cid, int consumeThread) {
//        return consumer(cid, consumeThread, defaultConsumerModel);
//    }
//
//    @Override
//    public ConsumerBuild consumer(String cid, String consumerModel) {
//        return consumer(cid, defaultConsumerThread, consumerModel);
//    }
//
//    @Override
//    public ConsumerBuild consumer(String cid, int consumeThread, String consumerModel) {
//        ConsumerId consumerId = consumerMap.get(cid);
//        if (null == consumerId) {
//            consumerId = new ConsumerId(cid, consumeThread, consumerModel);
//            consumerMap.put(cid, consumerId);
//        }
//        return new ConsumerBuildImpl(consumerId);
//    }
}
