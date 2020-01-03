package com.lr.spring.framework.aop.support;/**
 * @Auther: 45417
 * @Date: 2020/1/2 20:05
 * @Description:
 */

import ch.qos.logback.core.LogbackException;
import com.lr.spring.framework.aop.LRAopConfig;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRAdvicedSupport
 * @Description:
 * @Author: 45417
 * @Date: 2020/1/2 20:05
 * @version v1.0
 *主要用来解析和封装aop的配置类
 */
public class LRAdvicedSupport {

    private Class targetClass;//

    private Object target;//

    private Pattern pointCutClassPattern;

    private transient Map<Method, List<Object>> methodCache;

    private LRAopConfig config;

    public LRAdvicedSupport(LRAopConfig config) {
        this.config = config;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<Object> getInterceptorAndDynamicInterceptionAdvice(Method method,Class<?> targetClass) throws Exception{
        List<Object> cache = methodCache.get(method);
        //缓存未命中，则进行下一步处理
        if(cache == null){
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            cache = methodCache.get(m);
            //存入缓存
            this.methodCache.put(m,cache);
        }
        return cache;
    }

    public boolean pointCutMatch(){
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    private void parse(){
        //pointCut表达式
        String pointCut = config.getPointCut().replaceAll("\\.","\\\\").replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");
        String pointCutForClass = pointCut.substring(0,pointCut.lastIndexOf("\\(")-4);
        pointCutClassPattern = Pattern.compile("class"+pointCutForClass.substring(pointCutForClass.lastIndexOf(" ")+1));

        methodCache = new HashMap<>();
        Pattern pattern = Pattern.compile(pointCut);

        try {
            Class<?> aspectClass =  Class.forName(config.getAspectClass());
            Map<String,Method> aspectMethods = new HashMap<>();
            for(Method m:aspectClass.getMethods()){
                aspectMethods.put(m.getName(),m);
            }
            //在这里得到的方法都是原生方法。
            for(Method m:targetClass.getMethods()){
                String methodString = m.toString();
                if(methodString.contains("throws")){
                    methodString= methodString.substring(0,methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if(matcher.matches()){
                    //能满足切面规则的类，调价到aop配置中
                    List<Object> advices = new LinkedList<>();
                    //前置通知
                    if(!(null == config.getAspectBefore() || "".equals(config.getAspectBefore().trim()))){
//                        advices.add(new LRMethod)
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
