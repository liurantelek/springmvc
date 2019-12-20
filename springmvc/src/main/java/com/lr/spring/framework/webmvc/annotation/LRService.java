package com.lr.spring.framework.webmvc.annotation;

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;

import java.lang.annotation.*;

/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:49
 * @Description: 业务逻辑，注入接口
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LRService {
    String value() default "";
}
