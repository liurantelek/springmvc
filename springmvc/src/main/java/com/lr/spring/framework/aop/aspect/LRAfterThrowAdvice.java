package com.lr.spring.framework.aop.aspect;

import com.lr.spring.framework.aop.intercept.LRMethodInterceptor;
import com.lr.spring.framework.aop.intercept.LRMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author liuran
 * @create 2020-01-04-11:08
 * 异常通知
 */
public class LRAfterThrowAdvice extends LRAbstractAspectAdvice implements LRAdvice ,LRMethodInterceptor {

    private String throwingName;

    private LRMethodInvocation mi;

    public LRAfterThrowAdvice(Method aspectMethod, Object aspectTarget) {
        super (aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(LRMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed ();
        } catch (Throwable throwable) {
            invokeAdviceMethod (mi,null,throwable.getCause ());
            return throwable;
        }

    }

    public void setThrowingName(String name){
        this.throwingName = name;
    }


}
