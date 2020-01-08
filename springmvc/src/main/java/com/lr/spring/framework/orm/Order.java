package com.lr.spring.framework.orm;/**
 * @Auther: 45417
 * @Date: 2020/1/7 19:32
 * @Description:
 */

import com.sun.org.apache.xpath.internal.operations.Or;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: Order
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/7 19:32
 * @version v1.0
 *主要用于封装排序规则
 */
public class Order {
    private boolean ascending;//升序还是降序
    private String propertyName;//那个字段升序，那个字段降序

    @Override
    public String toString() {
        return propertyName+' '+(ascending?"asc":"desc");
    }

    protected Order(String propertyName,boolean ascending){
        this.propertyName = propertyName;
        this.ascending =ascending;
    }

    public static Order asc(String propertyName){
        return new Order(propertyName,true);
    }

    public static Order desc(String propertyName){
        return new Order(propertyName,false);
    }
}
