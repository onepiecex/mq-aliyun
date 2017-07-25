# mq-aliyun

## 生产者

### 定义发送地址
```java
@Pid("PID_TEST")
public enum  TestProducer {
    @To(topic = "TEST", tag = "dish.add")
    DISH_ADD,

    @To(topic = "TEST", tag = "dish.update")
    DISH_UPDATE,

    @To(topic = "TEST", tag = "dish.del")
    DISH_DEL
}
```
### 消息发送
```java
producerFactory.sendAsync(TestProducer.DISH_ADD, new Dish(1L, "name"));

producerFactory.sendAsync(TestProducer.DISH_UPDATE,
    new Dish(2L, "name"),
    new DeliveryOption("key").setDeliverTime(System.currentTimeMillis() + 1000 * 60));

producerFactory.sendAsync(TestProducer.DISH_DEL, 1L, new SendCallback() {
    @Override
    public void onSuccess(SendResult sendResult) {
        //send success
        //do some thing...
    }

    @Override
    public void onException(OnExceptionContext context) {
        //send fail
        //do some thing...
    }
});
```

### 自定义发送消息的序列方式

`默认使用fastJson 序列化`


# resources
```
在 com.github.mq.producer.ProducerSerialize 里 指定你的实现类

resources
	|-->  META-INF // 外卖
		|--> services
		    |--> com.github.mq.producer.ProducerSerialize
		
-- resources
  -- META-INF
    -- services
      -- com.github.mq.producer.ProducerSerialize


```

```java
public class MyProducerSerialize implements ProducerSerialize {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] objToBytes(Object object) {
        //do some thine
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            throw new RuntimeException("序列化失败");
        }

    }
}
```


## 消费者定义

使用声明式集中定义, 方便后期维护

```java
public class Consumers implements ConsumerAble {

    @Override
    public void init(Ons ons) {
        ons.consumer("CID_TEST_DISH")
                .subscribeTopic("TEST")
                .subscribeTag("dish.add || dish.update",TestConsumer::dishAdd)
                .subscribeTag("dish.del",TestConsumer::dishDel);

    }
}
```

```java
@Reconsume(5)
public class TestConsumer {
    @Reconsume(3)
    public Action dishAdd(Dish dish){
        //do some thine
        return Action.CommitMessage;
    }


    public Action dishDel(Long dishId, Message message){
        //Message 为阿里云mq 原生消息体

        //do some thing
        return Action.CommitMessage;
    }
}

```