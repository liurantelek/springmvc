package com.lr.spring.framework.aop.aspect;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liuran
 * @create 2020-01-04-10:27
 * 封装拦截器回调的通用逻辑，在mini版本中主要封装了反射动态调用的方法
 */
public abstract class LRAbstractAspectAdvice {
    private Method aspectMethod;
    private Object aspectTarget;


    public LRAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    //反射动态迪调用方法
    protected Object invokeAdviceMethod(LRJoinPoint joinPoint,Object returnValue,Throwable ex) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] paramsTypes = this.aspectMethod.getParameterTypes ();
        if(null == paramsTypes || paramsTypes.length==0){
            return this.aspectMethod.invoke (aspectTarget);
        }else {
            Object[] args = new Object[paramsTypes.length];
            for(int i=0;i<paramsTypes.length;i++){
                if(paramsTypes[i] == LRJoinPoint.class){
                    args[i] = joinPoint;
                }else if(paramsTypes[i] == Throwable.class){
                    args[i] = ex;
                }else if(paramsTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke (aspectTarget,args);
        }

    }
}
