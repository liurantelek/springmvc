package com.lr.spring.framework.webmvc;/**
 * @Auther: 45417
 * @Date: 2019/12/25 18:45
 * @Description:
 */

import java.io.File;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRViewResolver
 * @Description: 原生的ViewResolver主要完成模板名称和模板解析引擎的匹配，通过在
 * Serlvet用resolveViewAndName()方法来获得模板所对应的的View，
 * @Author: 45417
 * @Date: 2019/12/25 18:45
 * @version v1.0
 *
 */
public class LRViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    private File templateRootDir;

    private String viewName;

    public LRViewResolver(String templateRoot){
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        this.templateRootDir = new File(templateRootPath);
    }

}
