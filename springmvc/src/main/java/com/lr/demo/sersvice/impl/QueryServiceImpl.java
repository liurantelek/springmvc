package com.lr.demo.sersvice.impl;/**
 * @Auther: 45417
 * @Date: 2019/12/27 19:02
 * @Description:
 */

import com.lr.demo.sersvice.IQueryService;
import com.lr.spring.framework.webmvc.annotation.LRService;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: QueryServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/27 19:02
 * @version v1.0
 *
 */
@LRService
@Slf4j
public class QueryServiceImpl implements IQueryService {
    @Override
    public String query(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "time:"+time;
        log.info("time:"+time);
        return json;
    }
}
