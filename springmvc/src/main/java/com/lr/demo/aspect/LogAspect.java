package com.lr.demo.aspect;
/**
 * @Auther: 45417
 * @Date: 2019/12/30 08:34
 * @Description:
 */

import com.lr.spring.framework.aop.aspect.LRJoinPoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LogAspect
 * @Description: 切面类
 * @Author: 45417
 * @Date: 2019/12/30 8:34
 * @version v1.0
 * 定义一个织入的切面逻辑，也就是要针对目标代理对象增强的逻辑，
 * 本类主要啊完成对方法调用的监控，监听目标方法每次执行所消耗的时间
 *
 */
@Slf4j
public class LogAspect {
    //在调用一个方法之前，执行before()方法
    private static final Logger LOGGER = LoggerFactory.getLogger (LogAspect.class);

    public void before(LRJoinPoint joinPoint){
        joinPoint.setUserAttribute ("start_time"+joinPoint.getMethod ().getName (),System.currentTimeMillis ());
        //这个方法的逻辑是由我们自己写的，

        LOGGER.info("Invoke Before Method! ,targetObject"+joinPoint.getThis ()+",args:"+joinPoint.getArguments ());
    }

    public void after(LRJoinPoint joinPoint){
        LOGGER.info ("invoke after method："+joinPoint.getMethod ()+",args:"+joinPoint.getArguments ()+",targetclass"+joinPoint.getThis ());
        long startTime = (long) joinPoint.getUserAttribute ("start_time");
        long endTime = System.currentTimeMillis ();
        LOGGER.info ("useTime"+(endTime=startTime)+"ms");
    }

    public void afterThrowing(LRJoinPoint joinPoint){
        LOGGER.info ("出现异常:target"+joinPoint.getThis ()+",args:"+joinPoint.getArguments ()+",method:"+joinPoint.getMethod ());
    }
}
