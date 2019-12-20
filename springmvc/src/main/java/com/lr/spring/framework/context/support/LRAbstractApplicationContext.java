package com.lr.spring.framework.context.support;/**
 * @Auther: 45417
 * @Date: 2019/12/20 10:17
 * @Description:
 */

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRAbstractApplicationContext
 * @Description: ioc实现类的顶层抽象类，实现ioc容器相关的公共逻辑
 * @Author: 45417
 * @Date: 2019/12/20 10:17
 * @version v1.0
 *
 */
public abstract class LRAbstractApplicationContext {
    /**
     * 受保护，只是提供予子类取实现重写
     * @throws Exception
     */
    public void refresh()throws Exception{};
}
