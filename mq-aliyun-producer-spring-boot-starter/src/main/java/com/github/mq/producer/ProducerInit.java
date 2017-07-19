package com.github.mq.producer;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.github.mq.core.scan.ClassScanner;
import com.github.mq.producer.impls.ProducerFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by wangziqing on 17/7/17.
 */
@Configuration
public class ProducerInit {

    private final static Logger logger = LoggerFactory.getLogger(ProducerInit.class);

    @Bean
    public ProducerFactory getProducer(DefaultListableBeanFactory beanFactory){
        Iterator<ProducerSerialize> producerServiceLoader = ServiceLoader.load(ProducerSerialize.class).iterator();
        ProducerSerialize producerSerialize;
        if (producerServiceLoader.hasNext()) {
            producerSerialize = producerServiceLoader.next();
        } else {
            producerSerialize = object -> JSONObject.toJSONBytes(object);
        }
        ProducerFactoryImpl producer = new ProducerFactoryImpl();
        beanFactory.autowireBean(producer);
        producer.init(producerSerialize);
        start(producer);
        return producer;
    }

    private void start(ProducerFactoryImpl producer) {
        PidMode pidMode = getPids();
        pidMode.getPids().forEach(pid -> new Thread(() -> producer.get(pid)).start());
        pidMode.getOrderPids().forEach(pid -> new Thread(() -> producer.get(pid)).start());
    }


    private PidMode getPids() {
        Set<String> pids = Sets.newHashSet();
        Set<String> orderPids = Sets.newHashSet();
        ClassScanner.scan(ProducerAble.class).forEach(c -> {
            try {
                Object[] enumConstants = c.getEnumConstants();
                if (null != enumConstants) {
                    for (Object enumConstant : enumConstants) {
                        Method getPid = enumConstant.getClass().getMethod("getPid");
                        getPid.setAccessible(true);
                        Method getTopic = enumConstant.getClass().getMethod("getTopic");
                        getPid.setAccessible(true);
                        Method getTag = enumConstant.getClass().getMethod("getTag");
                        getTag.setAccessible(true);
                        Method isOrdered = enumConstant.getClass().getMethod("isOrdered");
                        isOrdered.setAccessible(true);
                        //setAccessible true 提升反射性能

                        Object pid, topic, tag;
                        pid = getPid.invoke(enumConstant);
                        topic = getTopic.invoke(enumConstant);
                        tag = getTag.invoke(enumConstant);
                        boolean ordered = Boolean.valueOf(isOrdered.invoke(enumConstant).toString());
                        if (null == pid) {
                            logger.error(String.format("生产者:%s 的 pid不能为空", c));
                            continue;
                        }
                        if (null == topic) {
                            logger.error(String.format("生产者:%s 的 topic不能为空", c));
                            continue;
                        }
                        if (null == tag) {
                            logger.error(String.format("生产者:%s 的 tag不能为空", c));
                            continue;
                        }

                        if (ordered) {
                            orderPids.add(pid.toString());
                        } else {
                            pids.add(pid.toString());
                        }
                        logger.info(String.format("发现生产者: TOPIC:%s===========>TAG:%s  %s", topic, tag, c));
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return new PidMode(pids, orderPids);
    }

    private class PidMode {
        private Set<String> pids;
        private Set<String> orderPids;

        public PidMode(Set<String> pids, Set<String> orderPids) {
            this.pids = pids;
            this.orderPids = orderPids;
        }

        public Set<String> getPids() {
            return pids;
        }

        public Set<String> getOrderPids() {
            return orderPids;
        }
    }
}
