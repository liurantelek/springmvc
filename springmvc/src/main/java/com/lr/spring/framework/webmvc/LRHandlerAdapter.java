package com.lr.spring.framework.webmvc;/**
 * @Auther: 45417
 * @Date: 2019/12/25 18:36
 * @Description:
 */

import com.lr.spring.framework.webmvc.annotation.LRRequestMapping;
import com.lr.spring.framework.webmvc.annotation.LRRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRHandlerAdapter
 * @Description: 主要完成请求传递到服务端的参数列表与method实参列表的对应关系，完成参数值的转化工作
 *                  核心方法是handle()方法，在handle()方法中用反射来调用被适配的目标方法，并将转换包装好的参数列表传递过去
 * @Author: 45417
 * @Date: 2019/12/25 18:36
 * @version v1.0
 *      专人干专事
 */
public class LRHandlerAdapter {

    public boolean supports(Object handler){
        return (handler instanceof  LRHandlerMapping);
    }


    public LRModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LRHandlerMapping handlerMapping = (LRHandlerMapping) handler;
        //每个方法有一个参数列表，这里保存的是形参列表
        Map<String,Integer> paramMapping = new HashMap<>();
        //这里只是给出命名参数
        Annotation [] [] pa = handlerMapping.getMethod().getParameterAnnotations();
        for(int i = 0;i<pa.length;i++){
            for(Annotation a:pa[i]){
                if(a instanceof LRRequestParam){
                    String paramName = ((LRRequestParam) a).value();
                    if(!"".equals(paramName.trim())){
                        paramMapping.put(paramName,i);
                    }
                }
            }
        }

        //根据用户请求的参数信息，跟Method中的参数信息进行动态匹配
        //resp传进来的目的只有一个，将其赋值给方法参数，
        //只有当用户传过来的ModelAndView为空的时候，才会新建一个默认的

        //1、要准备好这个方法的形参列表
        //方法重载时形参的决定因素:参数的个数,参数的类型，参数顺序，方法的名字
        //只处理request和response
        Class<?> [] paramTypes = handlerMapping.getMethod().getParameterTypes();
        for(int i = 0;i<paramTypes.length;i++){
            Class<?> type = paramTypes[i];
            if(type == HttpServletRequest.class || type == HttpServletResponse.class){
                paramMapping.put(type.getName(),i);
            }
        }
        //2、得到自定义命名参数所在的位置
        //用户通过URL传过来的参数列表
        Map<String,String[]> reqParameterMap = request.getParameterMap();

        //3、构造实例参数
        Object[] paramValues = new Object[paramTypes.length];

        for(Map.Entry<String,String[]> param:reqParameterMap.entrySet()){
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]","").replaceAll("\\s","");
            if(!paramMapping.containsKey(param.getKey())){
                continue;
            }
            int index = paramMapping.get(param.getKey());

            //因为页面传过来的值都是String类型的，而在方法中定义的类型是千变万化的
            //所以要针对我们传过来的参数进行类型转换
            paramValues[index] = caseStringValue(value,paramTypes[index]);
        }
        return null;

    }

    private Object caseStringValue(String value, Class<?> clazz) {
        if(clazz == String.class){
            return value;
        }else if(clazz == Integer.class){
            return null;
        }else {
            return null;
        }


    }
}
