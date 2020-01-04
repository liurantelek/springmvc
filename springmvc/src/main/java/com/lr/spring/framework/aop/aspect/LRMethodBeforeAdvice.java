package com.lr.spring.framework.aop.aspect;

import com.lr.spring.framework.aop.intercept.LRMethodInterceptor;
import com.lr.spring.framework.aop.intercept.LRMethodInvocation;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liuran
 * @create 2020-01-04-10:52
 * 前置通知的具体实现类
 */
public class LRMethodBeforeAdvice extends LRAbstractAspectAdvice implements LRAdvice ,LRMethodInterceptor{

    private LRJoinPoint joinPoint;

    public LRMethodBeforeAdvice(Method aspectMethod, Object aspectTarget) {
        super (aspectMethod, aspectTarget);
    }

    public void before(Method method,Object[] args,Object target) throws InvocationTargetException, IllegalAccessException {
        invokeAdviceMethod (this.joinPoint,null,null);
    }

    @Override
    public Object invoke(LRMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        this.before (mi.getMethod (),mi.getArguments (),mi.getThis ());
        return mi.proceed ();
    }
}
