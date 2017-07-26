package com.github.mq.consumer.models;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.github.mq.consumer.parms.ArgumentExtractors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by wangziqing on 17/7/13.
 */
public class ConsumerId {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerId.class);

    private String cid;
    private String topic;
    private ConsumerOptional consumerOptional;


    private final Map<String, Tag> tagMap = new HashMap<>();
    private final Map<Class<?>, MethodAccess> check = new HashMap<>();
    private final DefaultListableBeanFactory beanFactory;
    private final boolean ordered;

    public String getTags() {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Tag>> entrySet = getTagMap().entrySet();
        for (Map.Entry<String, Tag> entry : entrySet) {
            sb.append(entry.getKey() + "||");
        }
        return sb.toString();
    }

    public void addTag(String tag, Method method) {
        if (tagMap.containsKey(tag)) {
            logger.warn(String.format("cid: %s, 存在相同的tag : %s", cid, tag));
        }
        Class<?> cls = method.getDeclaringClass();

        final Tag t = new Tag();
        t.setInvokeCls(cls);
        t.setMethodName(method.getName());
        Reconsume mReconsume = method.getAnnotation(Reconsume.class);
        if (null != mReconsume) {
            t.setReconsume(mReconsume.value());
        } else {
            Reconsume cReconsume = cls.getAnnotation(Reconsume.class);
            if (null != cReconsume) {
                t.setReconsume(cReconsume.value());
            }
        }
        MethodAccess methodAccess = check.get(cls);
        if (null == methodAccess) {
            methodAccess = MethodAccess.get(cls);
            check.put(cls, methodAccess);
        }
        t.setMethodAccess(methodAccess);
        t.setArgumentExtractors(ArgumentExtractors.getArgumentExtractor(method, method.getParameterTypes(), beanFactory));
        Stream.of(tag.split("\\|\\|")).forEach(tag_ -> tagMap.put(tag_.trim(), t));
    }

    public ConsumerId(String cid, ConsumerOptional consumerOptional, DefaultListableBeanFactory beanFactory, boolean ordered) {
        this.cid = cid;
        this.consumerOptional = consumerOptional;
        this.beanFactory = beanFactory;
        this.ordered = ordered;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public Map<String, Tag> getTagMap() {
        return tagMap;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public ConsumerOptional getConsumerOptional() {
        return consumerOptional;
    }

    public boolean isOrdered() {
        return ordered;
    }
}
