package com.github.mq.aliyun.models;

/**
 * Created by wangziqing on 17/7/13.
 */
public class ConsumerOptional {
    private int consumeThread;
    private String consumerModel;
    private int maxReconsume;

    public ConsumerOptional(){}


    public ConsumerOptional(int consumeThread,int maxReconsume){
        this.consumeThread = consumeThread;
        this.maxReconsume = maxReconsume;
    }

    public ConsumerOptional(int consumeThread,int maxReconsume,String consumerModel){
        this.consumeThread = consumeThread;
        this.maxReconsume = maxReconsume;
        this.consumerModel = consumerModel;
    }

    public int getConsumeThread() {
        return consumeThread;
    }

    public ConsumerOptional setConsumeThread(int consumeThread) {
        this.consumeThread = consumeThread;
        return this;
    }

    public String getConsumerModel() {
        return consumerModel;
    }

    public ConsumerOptional setConsumerModel(String consumerModel) {
        this.consumerModel = consumerModel;
        return this;
    }

    public int getMaxReconsume() {
        return maxReconsume;
    }

    public ConsumerOptional setMaxReconsume(int maxReconsume) {
        this.maxReconsume = maxReconsume;
        return this;
    }
}
