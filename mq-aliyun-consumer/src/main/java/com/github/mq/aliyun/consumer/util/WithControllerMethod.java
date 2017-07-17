package com.github.mq.aliyun.consumer.util;

/**
 * Created by wangziqing on 17/6/2.
 */
import com.github.mq.aliyun.consumer.util.ControllerMethods.*;

public interface WithControllerMethod<T> {

    T subscribeTag(String tag,ControllerMethods.ControllerMethod controllerMethod);

    default T subscribeTag(String tag,ControllerMethod0 controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A> T subscribeTag(String tag,ControllerMethod1<A> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B> T subscribeTag(String tag,ControllerMethod2<A, B> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }
}
