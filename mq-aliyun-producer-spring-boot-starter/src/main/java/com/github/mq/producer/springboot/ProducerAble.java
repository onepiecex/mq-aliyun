package com.github.mq.producer.springboot;

/**
 * Created by wangziqing on 16/3/3.
 */
public interface ProducerAble {
    String getTopic();

    String getTag();

    String getPid();

    boolean isOrdered();

    default ProducerAble build(String pid,String topic,String tag,boolean isOrdered){
        return new ProducerAble() {
            @Override
            public String getTopic() {
                return pid;
            }

            @Override
            public String getTag() {
                return topic;
            }

            @Override
            public String getPid() {
                return tag;
            }

            @Override
            public boolean isOrdered() {
                return isOrdered;
            }
        };
    }
}