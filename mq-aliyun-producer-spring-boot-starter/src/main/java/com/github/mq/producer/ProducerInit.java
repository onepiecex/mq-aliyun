package com.github.mq.producer;

import com.github.mq.core.scan.AnnotationClassScanner;
import com.github.mq.producer.impls.ProducerFactoryImpl;
import com.github.mq.producer.models.Pid;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * Created by wangziqing on 17/7/17.
 */
@Configuration
public class ProducerInit {

    private final static Logger logger = LoggerFactory.getLogger(ProducerInit.class);

    @Bean
    public ProducerFactory getProducer(DefaultListableBeanFactory beanFactory) {
        ProducerFactoryImpl producerFactory = new ProducerFactoryImpl();
        beanFactory.autowireBean(producerFactory);
        producerFactory.init();
        start(producerFactory);
        return producerFactory;
    }

    private void start(ProducerFactoryImpl producer) {
        PidMode pidMode = getPids();
        pidMode.getPids().forEach(pid -> new Thread(() -> producer.addProducer(pid)).start());
        pidMode.getOrderPids().forEach(pid -> new Thread(() -> producer.addOrdereProducer(pid)).start());
    }

    private PidMode getPids() {
        Set<String> pids = Sets.newHashSet();
        Set<String> orderPids = Sets.newHashSet();
        Set<Class<?>> classes = AnnotationClassScanner.scan(Pid.class);
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
