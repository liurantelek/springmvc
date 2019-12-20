package com.lr.spring.framework.core;
/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:59
 * @Description:
 */

/**
 *单例工厂的顶层设计
 * @ProjectName: springmvc
 * @ClassName: LRBeanFactory
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/20 9:59
 * @version v1.0
 *
 */
public interface LRBeanFactory {
    /**
     * 根据beanName从ioc容器中获取实例bean
     * @param beanName beanName
     * @return bean实例
     * @throws Exception
     */
    Object getBean(String beanName)throws Exception;

    public Object getBean(Class<?> beanClass) throws Exception;
}
