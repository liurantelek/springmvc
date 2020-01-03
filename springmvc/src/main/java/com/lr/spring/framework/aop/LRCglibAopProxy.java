package com.lr.spring.framework.aop;/**
 * @Auther: 45417
 * @Date: 2020/1/3 18:33
 * @Description:
 */

import com.lr.spring.framework.aop.support.LRAdvicedSupport;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRCglibAopProxy
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/3 18:33
 * @version v1.0
 *
 */
public class LRCglibAopProxy implements LRAopProxy {

    private LRAdvicedSupport config;

    public LRCglibAopProxy(LRAdvicedSupport config) {
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
