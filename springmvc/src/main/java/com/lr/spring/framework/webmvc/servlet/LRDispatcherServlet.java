package com.lr.spring.framework.webmvc.servlet;/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:12
 * @Description:
 */

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRDispatcherServlet
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/20 9:12
 * @version v1.0
 *
 */
public class LRDispatcherServlet extends HttpServlet {

    private final String LOCATION = "contextConfigLocation";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
