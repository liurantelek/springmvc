package com.lr.spring.framework.aop;

/**
 * @Auther: 45417
 * @Date: 2020/1/3 18:34
 * @Description:
 */
public interface LRAopProxy {
   //获得一个代理对象
    Object getProxy();

    //通过自定义类加载器获得一个代理对象
    Object getProxy(ClassLoader classLoader);
}
