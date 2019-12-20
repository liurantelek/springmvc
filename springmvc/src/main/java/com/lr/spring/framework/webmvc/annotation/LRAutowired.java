package com.lr.spring.framework.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:51
 * @Description:自动注入
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LRAutowired {
    String value() default "";
}
