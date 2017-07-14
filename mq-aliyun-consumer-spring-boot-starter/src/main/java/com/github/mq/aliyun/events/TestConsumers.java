package com.github.mq.aliyun.events;

import com.github.mq.aliyun.Hello;
import com.github.mq.aliyun.models.Action;
import com.github.mq.aliyun.models.Reconsume;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangziqing on 17/7/13.
 */
public class TestConsumers {

    @Autowired
    private Hello hello;

    @Reconsume(10)
    public Action orderCreate(){
        return Action.CommitMessage;
    }
}
