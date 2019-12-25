package com.lr.spring.framework.webmvc;/**
 * @Auther: 45417
 * @Date: 2019/12/25 18:36
 * @Description:
 */

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRHandlerAdapter
 * @Description: 主要完成请求传递到服务端的参数列表与method实参列表的对应关系，完成参数值的转化工作
 *                  核心方法是handle()方法，在handle()方法中用反射来调用被适配的目标方法，并将转换包装好的参数列表传递过去
 * @Author: 45417
 * @Date: 2019/12/25 18:36
 * @version v1.0
 *      专人干专事
 */
public class LRHandlerAdapter {

    public boolean supports(Object handler){
        return (handler instanceof  LRHandlerMapping);
    }



}
