package com.mq.aliyun.example.model;


import com.github.mq.consumer.parms.WithArgumentExtractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangziqing on 17/7/17.
 */
@WithArgumentExtractor(JackArgumentExtractor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface JackJson {
}
