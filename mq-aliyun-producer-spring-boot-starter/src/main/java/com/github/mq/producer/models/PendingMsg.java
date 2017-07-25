package com.github.mq.producer.models;

import com.aliyun.openservices.ons.api.SendCallback;

/**
 * Created by wangziqing on 17/7/25.
 */
public class PendingMsg {
    private Enum anEnum;
    private Object message;
    private DeliveryOption deliveryOption;
    private SendCallback sendCallback;

    public PendingMsg(){}

    public PendingMsg(Enum anEnum,Object message,DeliveryOption deliveryOption,SendCallback sendCallback){
        this.anEnum =anEnum;
        this.message = message;
        this.deliveryOption = deliveryOption;
        this.sendCallback = sendCallback;
    }


    public Enum getAnEnum() {
        return anEnum;
    }

    public void setAnEnum(Enum anEnum) {
        this.anEnum = anEnum;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public DeliveryOption getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(DeliveryOption deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public SendCallback getSendCallback() {
        return sendCallback;
    }

    public void setSendCallback(SendCallback sendCallback) {
        this.sendCallback = sendCallback;
    }
}
