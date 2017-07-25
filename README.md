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


## 自定义序列化方式


`默认使用 fastJson 进行序列化和反序列化`


### 这里我拿 jackJson 序列化 和 反序列化举例子

#### 自定义发送消息的序列方式
```
- resources
  - META-INF
    - services
      - com.github.mq.producer.ProducerSerialize
      
在 com.github.mq.producer.ProducerSerialize 里 指定你的实现类
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

#### 自定义反序列化方式

首先定义一个PARAMETER注解
```java
@WithArgumentExtractor(JackArgumentExtractor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface JackJson {
}
```
定义反序列化实现类
```java
public class JackArgumentExtractor implements ArgumentExtractor {

    private Class parameterClass;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(Annotation annotation, Class parameterClass) {
        this.parameterClass = parameterClass;
    }

    @Override
    public Result extract(Message message) {
        byte[] body = message.getBody();
        try {
            return Results.next( mapper.readValue(body,parameterClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Results.end(Action.CommitMessage);
    }
}
```
使用
```java
public Action jackjson(@JackJson Dish dish){
    //do some thine
    return Action.CommitMessage;
}
```

