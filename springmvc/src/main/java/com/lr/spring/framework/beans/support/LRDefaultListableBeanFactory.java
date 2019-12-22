package com.lr.spring.framework.beans.support;/**
 * @Auther: 45417
 * @Date: 2019/12/20 10:52
 * @Description:
 */

import com.lr.spring.framework.beans.config.LRBeanDefinition;
import com.lr.spring.framework.context.support.LRAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRDefaultListableBeanFactory
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/20 10:52
 * @version v1.0
 *
 */
public class LRDefaultListableBeanFactory extends LRAbstractApplicationContext {

    //存储注册信息的beanDefinition
    protected final Map<String, LRBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();


}
