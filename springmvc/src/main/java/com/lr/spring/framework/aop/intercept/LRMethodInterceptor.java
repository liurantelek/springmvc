package com.lr.spring.framework.aop.intercept;
/**
 * @Auther: 45417
 * @Date: 2020/1/2 19:49
 * @Description:
 */

/**
 *方法拦截器顶层接口
 * @ProjectName: springmvc
 * @ClassName: LRMethodInterceptor
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/2 19:49
 * @version v1.0
 *
 */
public interface LRMethodInterceptor {
    Object invoke(LRMethodInvocation mi)throws Throwable;
    }
