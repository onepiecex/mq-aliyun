package com.github.mq.aliyun.models;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
//    private int consumeThread;
//    private String consumerModel;

    private ConsumerOptional consumerOptional;

    private Map<String, Tag> tagMap = new HashMap<>();
    private Map<Class<?>, MethodAccess> check = new HashMap<>();


    public void invoke(String tag, Object[] parameters) {
        Tag t = tagMap.get(tag);
        if (null == t) {
            throw new RuntimeException(String.format("%s 没有注册 请检查MqConsumer的init配置", tag));
        }
        t.getMethodAccess().invoke(t.getInvokeObject(), t.getMethodName(), parameters);
    }

    public void addTag(String tag, Method method) {
        if (tagMap.containsKey(tag)) {
            logger.warn(String.format("cid: %s, 存在相同的tag : %s", cid, tag));
        }

        Stream.of(tag.split("\\|\\|")).forEach(tag_ -> {
            Class<?> cls = method.getDeclaringClass();
            Tag t = new Tag();
            t.setInvokeCls(cls);
            t.setMethodName(method.getName());
            MethodAccess methodAccess = check.get(cls);
            if (null == methodAccess) {
                methodAccess = MethodAccess.get(cls);
                check.put(cls, methodAccess);
            }
            t.setMethodAccess(methodAccess);
            tagMap.put(tag_.trim(), t);
        });
    }

    public String getTags() {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Tag>> entrySet = getTagMap().entrySet();
        for (Map.Entry<String, Tag> entry : entrySet) {
            sb.append(entry.getKey() + "||");
        }
        return sb.toString();
    }

    public ConsumerId(String cid,ConsumerOptional consumerOptional){
        this.cid = cid;
        this.consumerOptional = consumerOptional;
    }

    public ConsumerId(String cid, int consumeThread, String consumerModel) {
        this.cid = cid;
//        this.consumeThread = consumeThread;
//        this.consumerModel = consumerModel;
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
}
