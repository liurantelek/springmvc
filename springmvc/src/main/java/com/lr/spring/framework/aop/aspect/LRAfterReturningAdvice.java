package com.lr.spring.framework.aop.aspect;

import com.lr.spring.framework.aop.intercept.LRMethodInterceptor;
import com.lr.spring.framework.aop.intercept.LRMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liuran
 * @create 2020-01-04-11:04
 * 后置通知的具体实现
 */
public class LRAfterReturningAdvice extends LRAbstractAspectAdvice implements LRAdvice,LRMethodInterceptor {

    private LRJoinPoint joinPoint;

    public LRAfterReturningAdvice(Method aspectMethod, Object aspectTarget) {
        super (aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(LRMethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed ();
        this.joinPoint = mi;
        this.afterReturning (retVal,mi.getMethod (),mi.getArguments (),mi.getThis ());
        return retVal;
    }

    public void afterReturning(Object returnVlaue,Method method,Object[] args,Object target) throws InvocationTargetException, IllegalAccessException {
        invokeAdviceMethod (joinPoint,returnVlaue,null);
    }
}
