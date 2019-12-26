package com.lr.spring.framework.webmvc;/**
 * @Auther: 45417
 * @Date: 2019/12/26 19:30
 * @Description:
 */

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRView
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/26 19:30
 * @version v1.0
 *
 */
public class LRView {

    public static final String DEFAULT_CONTENT_TYPE = "text/html;charseet=utf-8";

    private File viewFile;

    public LRView(File viewFile) {
        this.viewFile = viewFile;
    }

    public String getContentType(){
        return DEFAULT_CONTENT_TYPE;
    }

    public void render(Map<String,?> model, HttpServletRequest request, HttpServletResponse response)throws Exception{
        StringBuilder sb = new StringBuilder();
        RandomAccessFile ra = new RandomAccessFile(this.viewFile,"r");

        try {
            String line = null;
            while(null != (line = ra.readLine())){
                line = new String(line.getBytes("ISO-8859-1"),"utf-8");
                Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}",Pattern.CASE_INSENSITIVE);
                Matcher match = pattern.matcher(line);
                while (match.find()){
                    String paramName = match.group();
                    paramName = paramName.replaceAll("￥\\{|\\}","");
                    Object paramValue = model.get(paramName);
                    if(null == paramValue){
                        continue;
                    }
                    //要把￥{}中间的这个字符串去取出来
                    line = match.replaceFirst(makeStringForRegExp(paramValue.toString()));
                    match = pattern.matcher(line);
                }
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ra.close();
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(sb.toString());
    }

    //处理特殊字符
    private static String makeStringForRegExp(String str) {
        return str.replace("\\+","\\\\").replace("*","\\*")
                .replace("+","\\+").replace("|","\\|")
                .replace("{","\\{").replace("}","\\}")
                .replace("(","\\(").replace(")","\\)")
                .replace("^","\\^").replace("$","\\$")
                .replace("[","\\[").replace("]","\\]")
                .replace("?","\\?").replace(",","\\,")
                .replace(".","\\.").replace("&","\\&");
    }
}
