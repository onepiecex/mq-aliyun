package com.mq.aliyun.example.model;

import com.aliyun.openservices.ons.api.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mq.consumer.models.Action;
import com.github.mq.consumer.models.Result;
import com.github.mq.consumer.models.Results;
import com.github.mq.consumer.parms.ArgumentExtractor;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by wangziqing on 17/7/25.
 */
public class JackArgumentExtractor implements ArgumentExtractor {

    private Class parameterClass;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(Annotation annotation, Class parameterClass) {
        this.parameterClass = parameterClass;
    }

    @Override
    public Result extract(Message message) {
        byte[] body = message.getBody();
        try {
            return Results.next( mapper.readValue(body,parameterClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Results.end(Action.CommitMessage);
    }
}
