package com.github.mq.aliyun.consumer.models;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.github.mq.aliyun.consumer.parms.ArgumentExtractor;

/**
 * Created by wangziqing on 17/7/13.
 */
public class Tag {
    private MethodAccess methodAccess;
    private Object invokeObject;
    private Class invokeCls;
    private String methodName;
    private Integer reconsume;

    private ArgumentExtractor[] argumentExtractors;

    public Object invoke(Object[] parms){
        return methodAccess.invoke(invokeObject,methodName,parms);
    }

    public MethodAccess getMethodAccess() {
        return methodAccess;
    }

    public void setMethodAccess(MethodAccess methodAccess) {
        this.methodAccess = methodAccess;
    }

    public Class getInvokeCls() {
        return invokeCls;
    }

    public void setInvokeCls(Class invokeCls) {
        this.invokeCls = invokeCls;
    }

    public Object getInvokeObject() {
        return invokeObject;
    }

    public void setInvokeObject(Object invokeObject) {
        this.invokeObject = invokeObject;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getReconsume() {
        return reconsume;
    }

    public void setReconsume(Integer reconsume) {
        this.reconsume = reconsume;
    }

    public ArgumentExtractor[] getArgumentExtractors() {
        return argumentExtractors;
    }

    public void setArgumentExtractors(ArgumentExtractor[] argumentExtractors) {
        this.argumentExtractors = argumentExtractors;
    }
}
