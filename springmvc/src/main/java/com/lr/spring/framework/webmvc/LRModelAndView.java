package com.lr.spring.framework.webmvc;/**
 * @Auther: 45417
 * @Date: 2019/12/25 18:41
 * @Description:
 */

import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: ModelAndView
 * @Description: ModelAndView 主要用于封装页面模板和要往页面模板传送的参数的对应关系
 * @Author: 45417
 * @Date: 2019/12/25 18:41
 * @version v1.0
 *
 */
public class LRModelAndView {
    private String viewName;//页面模板的名称
    private Map<String,?> model;//往页面传送的参数

    public LRModelAndView (String viewName){
        this(viewName,null);
    }

    public LRModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
