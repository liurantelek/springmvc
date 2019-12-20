package com.lr.spring.framework.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:57
 * @Description:请求参数映射
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LRRequestParam {
    String value() default "";
    }
