package com.github.mq.producer;

import com.github.mq.producer.impls.ProducerFactoryImpl;
import com.github.mq.producer.models.Pid;
import com.google.common.collect.Sets;
import org.reflections.Reflections;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import java.util.Set;

/**
 * Created by wangziqing on 17/7/17.
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class ProducerInit {

    @Bean
    @ConditionalOnMissingBean
    public ProducerFactory getProducer(AbstractAutowireCapableBeanFactory beanFactory) {
        ProducerFactoryImpl producerFactory = new ProducerFactoryImpl();
        producerFactory.init(beanFactory.getBean(Environment.class));
        start(producerFactory);
        return producerFactory;
    }

    private void start(ProducerFactoryImpl producer) {
        Reflections reflections = new Reflections(producer.packages());
        PidMode pidMode = getPids(reflections.getTypesAnnotatedWith(Pid.class));
        pidMode.getPids().forEach(pid -> new Thread(() -> producer.addProducer(pid)).start());
        pidMode.getOrderPids().forEach(pid -> new Thread(() -> producer.addOrdereProducer(pid)).start());
    }


    public static PidMode getPids( Set<Class<?>> classes) {
        Set<String> pids = Sets.newHashSet();
        Set<String> orderPids = Sets.newHashSet();

        for (Class<?> cls : classes) {
            Object[] enumConstants = cls.getEnumConstants();
            if (null != enumConstants) {
                Pid pid = cls.getAnnotation(Pid.class);
                if (pid.ordered()) {
                    orderPids.add(pid.value());
                } else {
                    pids.add(pid.value());
                }
            }
        }
        return new PidMode(pids, orderPids);
    }

    public static class PidMode {
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
