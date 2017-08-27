package com.mq.aliyun.example.consumers;

import com.github.mq.consumer.models.Action;
import com.github.mq.consumer.models.Message;
import com.github.mq.consumer.models.Reconsume;
import com.mq.aliyun.example.model.Dish;
import com.mq.aliyun.example.model.JackJson;

/**
 * Created by wangziqing on 17/7/25.
 */
@Reconsume(5)
public class TestConsumer {
    @Reconsume(3)
    public Action dishAdd(Dish dish){
        //do some thine
        return Action.commit;
    }


    public Action dishDel(Long dishId, Message message){
        //Message 为阿里云mq 原生消息体

        //do some thing
        return Action.commit;
    }

    public Action jackjson(@JackJson Dish dish){
        //do some thine
        return Action.commit;
    }
}
