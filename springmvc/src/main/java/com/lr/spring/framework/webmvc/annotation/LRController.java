package com.lr.spring.framework.webmvc.annotation;/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:54
 * @Description:
 */

import java.lang.annotation.*;

/**
 *页面交互注解
 * @ProjectName: springmvc
 * @ClassName: LRController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/20 9:54
 * @version v1.0
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LRController {
    String value() default "";
}
