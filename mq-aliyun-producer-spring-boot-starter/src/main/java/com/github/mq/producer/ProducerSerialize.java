package com.github.mq.producer;

/**
 * Created by wangziqing on 17/7/19.
 */
public interface ProducerSerialize {

    byte[] objToBytes(Object object);
}
