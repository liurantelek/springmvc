package com.lr.spring.framework.aop.intercept;/**
 * @Auther: 45417
 * @Date: 2020/1/2 19:53
 * @Description:
 */

import com.lr.spring.framework.aop.aspect.LRJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRMethodInvocation
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/2 19:53
 * @version v1.0
 * 执行拦截器链，相当于Spring中ReflectiveMethodInvocation的功能
 *
 */
public class LRMethodInvocation implements LRJoinPoint{

    private Object proxy;//代理对象

    private Method method;//代理的目标方法

    private Object target;//代理的目标对象

    private Class<?> targetClass;//代理的目标类

    private Object[] arguments;//代理的方法的实参列表

    private List<Object> interceptorsAndDynamicMethodMatchers;//回调方法链

    //自定义属性
    private Map<String,Object> userAttributes;

    private int currentInterceptorIndex = -1;

    public LRMethodInvocation(Object proxy, Object target, Method method, Object[] args, Class targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = args;
        this.targetClass = targetClass;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable {
        //如果是Interceptor执行完了，则执行joinPoint
        if(this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size()-1){
            return this.method.invoke(this.target,this.arguments);
        }
        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        //如果要动态匹配joinPoint
        if(interceptorOrInterceptionAdvice instanceof LRMethodInterceptor){
            LRMethodInterceptor mi = (LRMethodInterceptor) interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        }else {
            return proceed();
        }
   
    }

    public Method getMethod() {
        return this.method;
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    public Object getThis(){
        return this;
    }

    @Override
    public void setUserAttribute(String key,Object value ){
        if(value != null){
            if(this.userAttributes == null){
                this.userAttributes = new HashMap<>();
            }
            this.userAttributes.put(key,value);
        }else {
            if(this.userAttributes != null){
                this.userAttributes.remove(key);
            }
        }
    }

    public Object getUserAttribute(String key){
        return (this.userAttributes != null ?this.userAttributes:null);
    }
}
