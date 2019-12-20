package com.lr.spring.framework.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:56
 * @Description:请求url
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LRRequestMapping {
    String value() default "";
}
