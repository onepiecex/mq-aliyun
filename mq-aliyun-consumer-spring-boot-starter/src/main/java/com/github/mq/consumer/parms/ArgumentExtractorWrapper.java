package com.github.mq.consumer.parms;

import com.aliyun.openservices.ons.api.Message;
import com.github.mq.consumer.models.Result;

import java.lang.annotation.Annotation;

/**
 * Created by wangziqing on 17/7/26.
 */
public class ArgumentExtractorWrapper {
    private ArgumentExtractor<?> argumentExtractor;
    private Annotation annotation;
    private Class<?> parameterClass;


    public Result excuteExtract(Message message){
        return argumentExtractor.extract(message,parameterClass,annotation);
    }

    public static ArgumentExtractorWrapper build(ArgumentExtractor argumentExtractor,Annotation annotation,Class<?> parameterClass){
        ArgumentExtractorWrapper extractorWrapper = new ArgumentExtractorWrapper();
        extractorWrapper.setArgumentExtractor(argumentExtractor);
        extractorWrapper.setAnnotation(annotation);
        extractorWrapper.setParameterClass(parameterClass);
        return extractorWrapper;
    }

    public ArgumentExtractor<?> getArgumentExtractor() {
        return argumentExtractor;
    }

    public void setArgumentExtractor(ArgumentExtractor<?> argumentExtractor) {
        this.argumentExtractor = argumentExtractor;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Class<?> getParameterClass() {
        return parameterClass;
    }

    public void setParameterClass(Class<?> parameterClass) {
        this.parameterClass = parameterClass;
    }
}
