package com.lr.spring.framework.aop;/**
 * @Auther: 45417
 * @Date: 2020/1/2 19:55
 * @Description:
 */

import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRAopConfig
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/2 19:55
 * @version v1.0
 *AOP配置封装
 */
public class LRAopConfig {
    //以下配置与properties文件中的属性一一对应
    private String pointCut;//切面表达式

    private String aspectBefore;//前置通知方法名字

    private String aspectAfter;//后置通知方法名字

    private String aspectClass;//要织入的切面类

    private String aspectAfterThrow;//异常通知方法名称

    private String aspectAfterThrowingName;//需要通知的异常类型

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getAspectBefore() {
        return aspectBefore;
    }

    public void setAspectBefore(String aspectBefore) {
        this.aspectBefore = aspectBefore;
    }

    public String getAspectAfter() {
        return aspectAfter;
    }

    public void setAspectAfter(String aspectAfter) {
        this.aspectAfter = aspectAfter;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }

    public String getAspectAfterThrow() {
        return aspectAfterThrow;
    }

    public void setAspectAfterThrow(String aspectAfterThrow) {
        this.aspectAfterThrow = aspectAfterThrow;
    }

    public String getAspectAfterThrowingName() {
        return aspectAfterThrowingName;
    }

    public void setAspectAfterThrowingName(String aspectAfterThrowingName) {
        this.aspectAfterThrowingName = aspectAfterThrowingName;
    }
}
