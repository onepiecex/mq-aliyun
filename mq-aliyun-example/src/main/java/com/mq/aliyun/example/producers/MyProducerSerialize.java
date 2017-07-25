package com.mq.aliyun.example.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mq.producer.ProducerSerialize;

/**
 * Created by wangziqing on 17/7/25.
 */
public class MyProducerSerialize implements ProducerSerialize {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] objToBytes(Object object) {
        //do some thine
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            throw new RuntimeException("序列化失败");
        }

    }
}
