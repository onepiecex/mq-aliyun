/**
 * Created by wangziqing on 17/06/02.
 */
package com.github.mq.aliyun.consumer.util;


import com.github.mq.aliyun.consumer.models.Action;

import java.io.Serializable;

/**
 * Created by wangziqing on 17/7/17.
 */
public class ControllerMethods {
    public interface ControllerMethod extends Serializable { }

    public interface ControllerOrderedMethod extends Serializable { }
    
    @FunctionalInterface
    public interface ControllerMethod0 extends ControllerMethod {
        Action apply() throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod1<A> extends ControllerMethod {
        Action apply(A a) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod2<A,B> extends ControllerMethod {
        Action apply(A a, B b) throws Exception;
    }
}