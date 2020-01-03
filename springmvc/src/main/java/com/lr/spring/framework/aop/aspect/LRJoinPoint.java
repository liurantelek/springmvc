package com.lr.spring.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @Auther: 45417
 * @Date: 2020/1/2 19:29
 * @Description:回调连接点，通过它可以获得被代理的业务方法的所有信息
 */
public interface LRJoinPoint {

    Method getMethod();//业务方法本身

    Object[] getArguments();//该方法的参数列表

    Object getThis();//该方法所属的实例对象

    //在JoinPoint中添加自定义属性
    void setUserAttribute(String key,Object value);

    //从已添加的自定义属性中获取一个属性值
    Object getUserAttribute(String key);

}
