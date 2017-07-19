package com.github.mq.producer.springboot;

/**
 * Created by wangziqing on 17/7/19.
 */
public interface ProducerSerialize {

    byte[] objToBytes(Object object);
}
