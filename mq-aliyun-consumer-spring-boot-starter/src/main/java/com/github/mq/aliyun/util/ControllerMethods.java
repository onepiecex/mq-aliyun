/**
 * Created by wangziqing on 17/06/02.
 */
package com.github.mq.aliyun.util;


import com.github.mq.aliyun.models.Action;

import java.io.Serializable;

/**
 * Functional interfaces for Ninja controller methods accepting up to X
 * number of arguments with type inference.
 */
public class ControllerMethods {

    /**
     * Marker interface that all functional interfaces will extend.  Useful for
     * simple validation an object is a ControllerMethod.
     */
    public interface ControllerMethod extends Serializable { }
    
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
    
    @FunctionalInterface
    public interface ControllerMethod3<A,B,C> extends ControllerMethod {
        Action apply(A a, B b, C c) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod4<A,B,C,D> extends ControllerMethod {
        Action apply(A a, B b, C c, D d) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod5<A,B,C,D,E> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod6<A,B,C,D,E,F> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod7<A,B,C,D,E,F,G> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod8<A,B,C,D,E,F,G,H> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod9<A,B,C,D,E,F,G,H,I> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h, I i) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod10<A,B,C,D,E,F,G,H,I,J> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod11<A,B,C,D,E,F,G,H,I,J,K> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod12<A,B,C,D,E,F,G,H,I,J,K,L> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod13<A,B,C,D,E,F,G,H,I,J,K,L,M> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n) throws Exception;
    }
    
    @FunctionalInterface
    public interface ControllerMethod15<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O> extends ControllerMethod {
        Action apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o) throws Exception;
    }
    
    // if you need more than 15 arguments then we recommend using the
    // legacy Class, methodName strategy
    
    // helper methods to allow classes to accept `ControllerMethod` but still
    // have the compiler create the correct functional method under-the-hood
    
    static public ControllerMethod0 of(ControllerMethod0 functionalMethod) {
        return functionalMethod;
    }
    
    static public <A> ControllerMethod1<A> of(ControllerMethod1<A> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B> ControllerMethod2<A,B> of(ControllerMethod2<A,B> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C> ControllerMethod3<A,B,C> of(ControllerMethod3<A,B,C> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D> ControllerMethod4<A,B,C,D> of(ControllerMethod4<A,B,C,D> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E> ControllerMethod5<A,B,C,D,E> of(ControllerMethod5<A,B,C,D,E> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F> ControllerMethod6<A,B,C,D,E,F> of(ControllerMethod6<A,B,C,D,E,F> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G> ControllerMethod7<A,B,C,D,E,F,G> of(ControllerMethod7<A,B,C,D,E,F,G> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H> ControllerMethod8<A,B,C,D,E,F,G,H> of(ControllerMethod8<A,B,C,D,E,F,G,H> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H,I> ControllerMethod9<A,B,C,D,E,F,G,H,I> of(ControllerMethod9<A,B,C,D,E,F,G,H,I> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H,I,J> ControllerMethod10<A,B,C,D,E,F,G,H,I,J> of(ControllerMethod10<A,B,C,D,E,F,G,H,I,J> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H,I,J,K> ControllerMethod11<A,B,C,D,E,F,G,H,I,J,K> of(ControllerMethod11<A,B,C,D,E,F,G,H,I,J,K> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H,I,J,K,L> ControllerMethod12<A,B,C,D,E,F,G,H,I,J,K,L> of(ControllerMethod12<A,B,C,D,E,F,G,H,I,J,K,L> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H,I,J,K,L,M> ControllerMethod13<A,B,C,D,E,F,G,H,I,J,K,L,M> of(ControllerMethod13<A,B,C,D,E,F,G,H,I,J,K,L,M> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H,I,J,K,L,M,N> ControllerMethod14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> of(ControllerMethod14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> functionalMethod) {
        return functionalMethod;
    }
    
    static public <A,B,C,D,E,F,G,H,I,J,K,L,M,N,O> ControllerMethod15<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O> of(ControllerMethod15<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O> functionalMethod) {
        return functionalMethod;
    }
    
}