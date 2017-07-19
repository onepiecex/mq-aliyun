package com.github.mq.consumer.util;

/**
 * Created by wangziqing on 17/6/2.
 */

public interface WithControllerMethod<T> {

    T subscribeTag(String tag, ControllerMethods.ControllerMethod controllerMethod);

    default T subscribeTag(String tag, ControllerMethods.ControllerMethod0 controllerMethod) {
        return subscribeTag(tag,(ControllerMethods.ControllerMethod)controllerMethod);
    }

    default <A> T subscribeTag(String tag, ControllerMethods.ControllerMethod1<A> controllerMethod) {
        return subscribeTag(tag,(ControllerMethods.ControllerMethod)controllerMethod);
    }

    default <A,B> T subscribeTag(String tag, ControllerMethods.ControllerMethod2<A, B> controllerMethod) {
        return subscribeTag(tag,(ControllerMethods.ControllerMethod)controllerMethod);
    }
}
