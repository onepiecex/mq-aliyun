package com.github.mq.consumer;

import com.github.mq.consumer.impls.OnsImpl;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by wangziqing on 17/7/12.
 */
public class ReadyEvent implements ApplicationListener<ApplicationReadyEvent> {

    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationReadyEvent.getApplicationContext().getBeanFactory();
        new OnsImpl(beanFactory).start();
    }
}
