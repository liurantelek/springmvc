package com.lr.spring.framework.aop;/**
 * @Auther: 45417
 * @Date: 2020/1/3 18:36
 * @Description:
 */

import com.lr.spring.framework.aop.intercept.LRMethodInvocation;
import com.lr.spring.framework.aop.support.LRAdvicedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRJdkDynamicAopProxy
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/3 18:36
 * @version v1.0
 * 使用jdk proxy api生成代理类
 *
 */
public class LRJdkDynamicAopProxy implements LRAopProxy, InvocationHandler {

    private LRAdvicedSupport config;

    public LRJdkDynamicAopProxy(LRAdvicedSupport config) {
        this.config = config;
    }

    //把原生的对象传进来
    @Override
    public Object getProxy() {
        return getProxy(this.config.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.config.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //将每一个JoinPoint也就是被代理的业务方法（Method）封装成一个拦截器，组合成一个拦截器链
        List<Object> interceptorsAndDynamicMethodMatchers = config.getInterceptorAndDynamicInterceptionAdvice(method,this.config.getTargetClass());
        //交给拦截器链MethodInvocation的proceed()方法执行
        LRMethodInvocation invocation = new LRMethodInvocation(proxy,this.config.getTarget(),method,args,this.config.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
     }
}
