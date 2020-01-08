package com.lr.spring.framework.orm;/**
 * @Auther: 45417
 * @Date: 2020/1/7 20:49
 * @Description:
 */

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @version v1.0
 * @ProjectName: springmvc
 * @ClassName: ClassMappings
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/7 20:49
 */
public class ClassMappings {
    //构造方法私有化
    private ClassMappings() {
    }

    static final Set<Class<?>> SUPPORTED_SQL_OBJECTS = new HashSet<>();

    static {
        Class<?>[] classes = {
                boolean.class, Boolean.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class,
                String.class,
                Date.class,
                Timestamp.class,
                BigDecimal.class
        };
        SUPPORTED_SQL_OBJECTS.addAll(Arrays.asList(classes));
    }

    static boolean isSupportedSQLObject(Class<?> clazz){
        return clazz.isEnum() || SUPPORTED_SQL_OBJECTS.contains(clazz);
    }

    public static Map<String, Method> findPublicGetters(Class<?> clazz){
        Map<String,Method> map = new HashMap<>();
        Method [] methods = clazz.getMethods();
        for(Method m:methods){
            if(Modifier.isStatic(m.getModifiers())){
                continue;
            }
            if(m.getParameterTypes().length!=0){
                continue;
            }
            if(m.getName().equals("getClass")){
                continue;
            }
            Class<?> returnType = m.getReturnType();
            if(void.class.equals(returnType)){
                continue;
            }
            if(!isSupportedSQLObject(returnType)){
                continue;
            }
            if((returnType.equals(boolean.class) || returnType.equals(Boolean.class))||m.getName().startsWith("is")||
            m.getName().length()>2){
                map.put(getGetterName(m),m);
                continue;
            }
            if(!m.getName().startsWith("get")){
                continue;
            }
            if(m.getName().length()<4){
                continue;
            }
            map.put(getGetterName(m),m);
        }
        return map;
    }

    public static Field [] findFields(Class<?> clazz){
        return clazz.getDeclaredFields();
    }

    public static Map<String,Method> findPublicSetters(Class<?> clazz){
        Map<String,Method> setters = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for(Method m:methods){
            if(Modifier.isStatic(m.getModifiers())){
                continue;
            }
            if(! void.class.equals(m.getReturnType())){
                continue;
            }
            if(m.getParameterTypes().length!=1){
                continue;
            }
            if(!m.getName().startsWith("set")){
                continue;
            }
            if(m.getName().length()<4){
                continue;
            }
            if(!isSupportedSQLObject(m.getParameterTypes()[0])){
                continue;
            }
            setters.put(getSetterName(m),m);
        }
        return setters;
    }

    private static String getSetterName(Method setter) {
        String name = setter.getName().substring(3);
        return Character.toLowerCase(name.charAt(0))+name.substring(1);
    }

    private static String getGetterName(Method getter) {
        String name = getter.getName();
        if(name.startsWith("is")){
            name = name.substring(2);
        }else {
            name = name.substring(3);
        }
        return Character.toLowerCase(name.charAt(0))+name.substring(1);
    }


}
