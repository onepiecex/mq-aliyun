package com.github.mq.aliyun.util;

/**
 * Created by wangziqing on 17/6/2.
 */
import com.github.mq.aliyun.util.ControllerMethods.*;

public interface WithControllerMethod<T> {

    T subscribeTag(String tag,ControllerMethods.ControllerMethod controllerMethod);

//    T with(ControllerMethod controllerMethod);
//    T blockingWith(ControllerMethod controllerMethod);

    //topic,tag,

    default T subscribeTag(String tag,ControllerMethod0 controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A> T subscribeTag(String tag,ControllerMethod1<A> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B> T subscribeTag(String tag,ControllerMethod2<A, B> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C> T subscribeTag(String tag,ControllerMethod3<A, B, C> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C,D> T subscribeTag(String tag,ControllerMethod4<A, B, C, D> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C,D,E> T subscribeTag(String tag,ControllerMethod5<A, B, C, D, E> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C,D,E,F> T subscribeTag(String tag,ControllerMethod6<A, B, C, D, E, F> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C,D,E,F,G> T subscribeTag(String tag,ControllerMethod7<A, B, C, D, E, F, G> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C,D,E,F,G,H> T subscribeTag(String tag,ControllerMethod8<A, B, C, D, E, F, G, H> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C,D,E,F,G,H,I> T subscribeTag(String tag,ControllerMethod9<A, B, C, D, E, F, G, H, I> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

    default <A,B,C,D,E,F,G,H,I,J> T subscribeTag(String tag,ControllerMethod10<A, B, C, D, E, F, G, H, I, J> controllerMethod) {
        return subscribeTag(tag,(ControllerMethod)controllerMethod);
    }

}
