package com.lr.spring.framework.webmvc;/**
 * @Auther: 45417
 * @Date: 2019/12/25 18:32
 * @Description:
 */

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRHandlerMapping
 * @Description: 主要用来保存URL和Method的对应关系，这里其实是策略模式
 * @Author: 45417
 * @Date: 2019/12/25 18:32
 * @version v1.0
 *
 */
public class LRHandlerMapping {
    private Object controller;//目标方法所在的controller对象
    private Method method;//URL对应的目标方法
    private Pattern pattern;//URL的封装

    public LRHandlerMapping(Pattern pattern, Object controller, Method method){
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
