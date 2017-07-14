package com.github.mq.aliyun.models;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * Created by wangziqing on 17/7/13.
 */
public class Tag {
    private MethodAccess methodAccess;
    private Object invokeObject;
    private Class invokeCls;
    private String methodName;

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
}
