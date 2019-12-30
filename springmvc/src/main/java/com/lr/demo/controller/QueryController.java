package com.lr.demo.controller;/**
 * @Auther: 45417
 * @Date: 2019/12/27 19:04
 * @Description:
 */

import com.lr.demo.sersvice.IQueryService;
import com.lr.spring.framework.webmvc.annotation.LRAutowired;
import com.lr.spring.framework.webmvc.annotation.LRController;
import com.lr.spring.framework.webmvc.annotation.LRRequestMapping;
import com.lr.spring.framework.webmvc.annotation.LRRequestParam;

import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: QueryController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/27 19:04
 * @version v1.0
 *
 */
@LRController
@LRRequestMapping("/demo")
public class QueryController {

    @LRAutowired
    IQueryService iQueryService;

    @LRRequestMapping("/query")
    public String getQueryResult(Map<String,Object> param,@LRRequestParam(value = "name") String name){
        return iQueryService.query("name");
    }
}
