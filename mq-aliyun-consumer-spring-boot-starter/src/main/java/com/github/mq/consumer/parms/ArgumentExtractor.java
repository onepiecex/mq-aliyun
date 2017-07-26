package com.github.mq.consumer.parms;

import com.aliyun.openservices.ons.api.Message;
import com.github.mq.consumer.models.Result;

import java.lang.annotation.Annotation;

/**
 * Created by wangziqing on 17/2/18.
 */
public interface ArgumentExtractor<T> {
    Result<T> extract(Message message, Class<?> parameterClass, Annotation annotation);

}
