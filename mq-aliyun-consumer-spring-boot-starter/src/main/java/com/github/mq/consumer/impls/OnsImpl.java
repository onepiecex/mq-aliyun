package com.github.mq.consumer.impls;

import com.github.mq.core.scan.ClassScanner;
import com.github.mq.core.scan.constant.MqConstant;
import com.google.common.base.Strings;
import com.github.mq.consumer.ConsumerBuild;
import com.github.mq.consumer.ConsumerAble;
import com.github.mq.consumer.Ons;
import com.github.mq.consumer.models.ConsumerId;
import com.github.mq.consumer.models.ConsumerOptional;
import com.github.mq.consumer.models.Tag;
import com.github.mq.consumer.parms.ArgumentExtractors;
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
    public static final String MQ_CONSUMER_DEFAULT_SUSPEND_TIME = "aliyun.mq.consumer.suspendTime";

    //SuspendTimeMillis

    private static int defaultConsumerThread = 20;
    private static String defaultConsumerModel = ConsumerAble.CLUSTERING;
    private static int defaultMaxReconsume = 16;
    private static int defaultSuspendTime = 100;

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
        ClassScanner.scan(ConsumerAble.class).forEach((Class<ConsumerAble> mqConsumerClass) -> {
            ConsumerAble consumer = ArgumentExtractors.instantiateComponent(beanFactory,mqConsumerClass,null);
            consumer.init(this);
        });
        consumerMap.forEach((cid, consumerId) -> {
            Map<String, Tag> tagMap = consumerId.getTagMap();
            for (Map.Entry<String, Tag> tagEntry : tagMap.entrySet()) {
                Tag tag = tagEntry.getValue();
                Object invokeObj = ArgumentExtractors.instantiateComponent(beanFactory, tag.getInvokeCls(), null);
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
        String consumerSuspendTime = env.getProperty(MQ_CONSUMER_DEFAULT_SUSPEND_TIME);

        if (null != consumerThread) {
            defaultConsumerThread = Integer.valueOf(defaultConsumerThread);
        }
        if (null != consumerMaxReconsume) {
            defaultMaxReconsume = Integer.valueOf(consumerMaxReconsume);
        }
        if(null != consumerSuspendTime){
            defaultSuspendTime = Integer.valueOf(consumerSuspendTime);
        }
        if (null != consumerModel && !consumerModel.isEmpty()) {
            defaultConsumerModel = consumerModel;
        }

    }

    @Override
    public void defaultTopic(String topic) {
        this.defaultTopic = topic;
    }

    @Override
    public ConsumerBuild consumer(String cid) {
        return consumer(cid, new ConsumerOptional());
    }

    public ConsumerBuild consumer(String cid, ConsumerOptional consumerOptional) {
        return new ConsumerBuildImpl(generateConsumerId(cid,consumerOptional,false));
    }

    @Override
    public ConsumerBuild consumerOrdered(String cid) {
        return consumerOrdered(cid, new ConsumerOptional());
    }

    @Override
    public ConsumerBuild consumerOrdered(String cid, ConsumerOptional consumerOptional) {
        return new ConsumerBuildImpl(generateConsumerId(cid,consumerOptional,true));
    }

    private ConsumerId generateConsumerId(String cid, ConsumerOptional consumerOptional, boolean ordered){
        ConsumerId consumerId = consumerMap.get(cid);
        if (null == consumerId) {
            if(null == consumerOptional.getConsumerModel() || Strings.isNullOrEmpty(consumerOptional.getConsumerModel())){
                consumerOptional.setConsumerModel(defaultConsumerModel);
            }
            if(null == consumerOptional.getConsumeThread()){
                consumerOptional.setConsumeThread(defaultConsumerThread);
            }
            if(null == consumerOptional.getMaxReconsume()){
                consumerOptional.setMaxReconsume(defaultMaxReconsume);
            }
            if(null == consumerOptional.getSuspendTime()){
                consumerOptional.setSuspendTime(defaultSuspendTime);
            }

            consumerId = new ConsumerId(cid, consumerOptional, beanFactory,ordered);
            consumerMap.put(cid, consumerId);
        }
        if (null != defaultTopic) {
            consumerId.setTopic(defaultTopic);
        }
        return consumerId;
    }
}
