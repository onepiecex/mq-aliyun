package consumers;

import com.github.mq.aliyun.consumer.MqConsumer;
import com.github.mq.aliyun.consumer.Ons;
import com.github.mq.aliyun.consumer.models.Action;
import com.github.mq.aliyun.consumer.models.Body;
import com.github.mq.aliyun.consumer.models.ConsumerOptional;

/**
 * Created by wangziqing on 17/7/17.
 */
public class Consumer implements MqConsumer {
    @Override
    public void init(Ons ons) {
        ons.defaultTopic("WEBSOCKET");

        ons.consumer("CID_WEBSOCKET_CHAT",new ConsumerOptional(10))
                .subscribeTag("test",Consumer::test);
    }

    public Action test(@Body String s){
        System.out.println(s);
        return Action.CommitMessage;
    }


}
