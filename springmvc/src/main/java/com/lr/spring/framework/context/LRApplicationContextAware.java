package com.lr.spring.framework.context;

/**主要通过实现侦听机制得到一个回调方法，从而得到Ioc容器的上下文，即ApplicationContext，
 *
 * 通过解耦方式获得ioc容器的顶层设计
 * 后面将通过一个监听器去扫描所有的类，只要实现了此接口
 * 即自动调用setApplicationContext()方法，从而获得ioc容器自动注入目标类中
 * @author liuran
 * @create 2019-12-21-16:30
 */
public interface LRApplicationContextAware {

    void setApplicationContext(LRApplicationContext applicationContext);
}
