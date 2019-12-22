package com.lr.spring.framework.beans.config;

/**
 * @author liuran
 * @create 2019-12-22-21:48
 */
public class LRBeanPostProcessor {

    //为在bean的初始化之前，提供回调入口
    public Object postProcessBeforeInitialization(Object bean,String beanName) throws Exception{
        return bean;
    }

    //为在bean的初始化之后提供回调入口
    public Object postProcessAfterInitialization(Object bean,String beanName)throws Exception{
        return bean;
    }
}
