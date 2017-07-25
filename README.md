# mq-aliyun
基于[阿里云MQ](https://help.aliyun.com/document_detail/29532.html?spm=5176.doc55448.6.539.oDO0oJ)的封装

## 使用
生产者
```xml
<dependency>
  <groupId>com.github.onepiecex</groupId>
  <artifactId>mq-aliyun-producer-spring-boot-starter</artifactId>
  <version>1.0</version>
</dependency>
```
消费者
```xml
<dependency>
  <groupId>com.github.onepiecex</groupId>
  <artifactId>mq-aliyun-consumer-spring-boot-starter</artifactId>
  <version>1.0</version>
</dependency>
```

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
有序的生产者
```java
@Pid(value = "PID_MEICANYUN" ,ordered = true)
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
`发送顺序消息时 需要使用orderSend发送标记了ordered = true 的生产者`
```java
producerFactory.orderSend(......);
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
//该类下面的所有消费者消费失败时重试5次
@Reconsume(5)
public class TestConsumer {
    
    //重试3次
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

## 配置文件 (application.yaml)
```yaml
aliyun :
  #阿里云授权KEY
  accessKey : XXXXXXX
  #阿里云秘钥
  secretKey : XXXXXXXXXX

  mq :
    #topic、cid、pid的后缀 (用于区分 开发 生产模式)
    suffix : _dev

    producer :
      #发送超时时间(毫秒)  缺省3000
      sendTimeOut : 1000

      #生产者路径,支持多个 以,分割
      packages : com.mq.aliyun.example

    consumer :
      #扫描实现了ConsumerAble的类的路径,支持多个 以,分割
      packages : com.mq.aliyun.example

      #默认消费线程数 , 缺省20
      defaultThread : 10

      #集群模式: CLUSTERING ,广播模式: BROADCASTING
      defaultModel : CLUSTERING

      #默认重试次数, 缺省16次
      defaultMaxReconsume : 5

      #顺序消息消费失败进行重试前的等待时间 单位(毫秒) , 默认100
      #仅顺序消息才会生效
      suspendTime : 200

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

也可以实现 DefaultArgumentExtractor, 替换默认的反序列化方式
```
- resources
  - META-INF
    - services
      - com.github.mq.consumer.DefaultArgumentExtractor
```

```java
public Action jackjson(Dish dish){
    //do some thine
    return Action.CommitMessage;
}
```

## License

Copyright (C) 2017 onepiece.x, Inc.

This work is licensed under the Apache License, Version 2.0. See LICENSE for details.