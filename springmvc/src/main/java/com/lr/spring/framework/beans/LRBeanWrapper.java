package com.lr.spring.framework.beans;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRBeanWrapper
 * @Description: 主要用于封装创建后的对象的实例，代理对象（Proxy Object）或者原生对象(Original Object)都由BeanWrapper来保存
 * @Author: 45417
 * @Date: 2019/12/20 10:10
 * @version v1.0
 *
 */
public class LRBeanWrapper {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public LRBeanWrapper(Object instance) {
    this.wrappedInstance = instance;
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    public void setWrappedInstance(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    /**
     * 返回代理以后的class
     * 可能会是这个&Proxy0
     * @return
     */
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
