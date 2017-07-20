package com.github.mq.producer.models;

/**
 * Created by wangziqing on 17/7/20.
 */
public class DeliveryOption {
    private String key;
    private Long deliverTime;
    public String getKey() {
        return key;
    }

    public DeliveryOption(){}
    public DeliveryOption(String key){
        this.key = key;
    }

    public DeliveryOption setKey(String key) {
        this.key = key;
        return this;
    }

    public Long getDeliverTime() {
        return deliverTime;
    }

    public DeliveryOption setDeliverTime(Long deliverTime) {
        this.deliverTime = deliverTime;
        return this;
    }
}
