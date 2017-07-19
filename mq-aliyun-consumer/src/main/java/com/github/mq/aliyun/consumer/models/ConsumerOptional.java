package com.github.mq.aliyun.consumer.models;

/**
 * Created by wangziqing on 17/7/13.
 */
public class ConsumerOptional {
    //消费线程数
    private Integer consumeThread;
    //消费模式(集群 : CLUSTERING, 广播 : BROADCASTING)
    private String consumerModel;
    //消息消费失败时的最大重试次数
    private Integer maxReconsume;
    //顺序消息消费失败进行重试前的等待时间 单位(毫秒)
    private Integer suspendTime;

    public ConsumerOptional(){}

    public ConsumerOptional(Integer consumeThread){
        this.consumeThread = consumeThread;
    }

    public ConsumerOptional(Integer consumeThread,Integer maxReconsume){
        this.consumeThread = consumeThread;
        this.maxReconsume = maxReconsume;
    }

    public ConsumerOptional(Integer consumeThread,Integer maxReconsume,String consumerModel){
        this.consumeThread = consumeThread;
        this.maxReconsume = maxReconsume;
        this.consumerModel = consumerModel;
    }

    public Integer getConsumeThread() {
        return consumeThread;
    }

    public ConsumerOptional setConsumeThread(Integer consumeThread) {
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

    public Integer getMaxReconsume() {
        return maxReconsume;
    }

    public ConsumerOptional setMaxReconsume(Integer maxReconsume) {
        this.maxReconsume = maxReconsume;
        return this;
    }

    public Integer getSuspendTime() {
        return suspendTime;
    }

    public ConsumerOptional setSuspendTime(Integer suspendTime) {
        this.suspendTime = suspendTime;
        return this;
    }
}
