package com.lr.spring.framework.beans.config;/**
 * @Auther: 45417
 * @Date: 2019/12/20 10:03
 * @Description:
 */

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRBeanDefinition
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/20 10:03
 * @version v1.0
 *用来存储配置文件中信息
 * 相当于是保存在内存中的配置
 */
public class LRBeanDefinition {
    private String beanClassName; //原生bean的全类名字
    private boolean lazyInit = false;//标志是否延迟加载
    private String  factoryBeanName;//保存beanName，在IOC容器中存储的key

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
