# mq-aliyun

## producer

### 定义发送地址
```java
@Pid(value = "pid_test" ,ordered = false)
public enum  TestProducer {
    @To(topic = "topic", tag = "send.mail")
    SEND_MAIL,

    @To(topic = "topic" , tag = "send.sms")
    SEND_SMS;
}
```
### send
```java
@Autowired
private ProducerFactory producerFactory;

producerFactory.sendAsync(TestProducer.SEND_MAIL, Object);
```