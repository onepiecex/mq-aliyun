package com.github.mq.aliyun.consumer.parms;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import com.github.mq.aliyun.consumer.models.Action;
import com.github.mq.aliyun.consumer.models.Result;
import com.github.mq.aliyun.consumer.models.Results;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by wangziqing on 17/7/17.
 */
public class ArgumentExtractors {

    private static final Logger logger = LoggerFactory.getLogger(ArgumentExtractors.class);

    private static final Map<Class<?>, ArgumentExtractor<?>> STATIC_EXTRACTORS =
            ImmutableMap.<Class<?>, ArgumentExtractor<?>>builder()
                    .put(Message.class, new MessageExtractor())
                    .build();

    public static ArgumentExtractor getArgumentExtractor(
            Class<?> parameterClass,
            Annotation[] annotations,
            DefaultListableBeanFactory beanFactory) {
        ArgumentExtractor<?> extractor = STATIC_EXTRACTORS.get(parameterClass);
        if (extractor == null) {
            for (Annotation annotation : annotations) {
                WithArgumentExtractor withArgumentExtractor = annotation.annotationType()
                        .getAnnotation(WithArgumentExtractor.class);
                if (withArgumentExtractor != null) {
                    extractor = instantiateComponent(beanFactory, withArgumentExtractor.value(), annotation);
                    extractor.init(annotation, parameterClass);
                    return extractor;
                }
            }
        }
        return extractor;
    }

    public static <T> T instantiateComponent(DefaultListableBeanFactory beanFactory, Class<T> cls, Annotation annotation) {
        T extractor;

        try {
            extractor = beanFactory.getBean(cls);
        } catch (BeansException e) {
            try {
                extractor = (T) beanFactory.getBean(cls.getName());
            } catch (BeansException e1) {
                BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(cls.getName());
                beanFactory.registerBeanDefinition(cls.getName(), beanDefinition.getBeanDefinition());
                extractor = (T) beanFactory.getBean(cls.getName());
            }
        }
        return extractor;
    }


    public static class BodyExtractor implements ArgumentExtractor {

        private Class parameterClass;

        @Override
        public void init(Annotation annotation, Class parameterClass) {
            this.parameterClass = parameterClass;
        }

        @Override
        public Result extract(Message message) {
            Object object;
            try {
                object = JSONObject.parseObject(message.getBody(), parameterClass);
            } catch (JSONException e) {
                e.printStackTrace();
                logger.error(String.format("%s 反序列化失败 by : %s",
                        parameterClass, JSONObject.parseObject(message.getBody(), Object.class)));
                return Results.end(Action.CommitMessage);
            }

            return Results.next(object);
        }
    }

    public static class MessageExtractor implements ArgumentExtractor<Message> {

        @Override
        public void init(Annotation annotation, Class<?> parameterClass) {
        }

        @Override
        public Result<Message> extract(Message message) {
            return Results.next(message);
        }
    }
}
