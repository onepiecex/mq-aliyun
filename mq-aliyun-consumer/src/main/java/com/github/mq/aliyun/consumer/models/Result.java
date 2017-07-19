package com.github.mq.aliyun.consumer.models;

/**
 * Created by wangziqing on 17/7/17.
 */
public class Result<T> {
    private T result;
    private Action action;

    public Result(T result) {
        this.result = result;
    }

    public Result(Action action) {
        this.action = action;
    }

    public Object getResult() {
        return result;
    }

    public Action getAction() {
        return action;
    }
}
