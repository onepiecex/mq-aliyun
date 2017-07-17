package com.github.mq.aliyun.consumer.models;

/**
 * Created by wangziqing on 17/7/17.
 */
public class Results {

    public static <T> Result<T> next(T result){
        return new Result(result);
    }

    public static Result end(Action action){
        return new Result(action);
    }
}
