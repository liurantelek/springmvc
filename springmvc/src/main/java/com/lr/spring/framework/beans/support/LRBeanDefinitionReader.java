package com.lr.spring.framework.beans.support;

import com.lr.spring.framework.beans.config.LRBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 对配置文件进行查找、读取、解析
 * @author liuran
 * @create 2019-12-21-15:59
 */
public class LRBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<> ();

    private Properties config = new Properties ();

    private final String SCAN_PACKAGE = "scanPackage";

    public LRBeanDefinitionReader(String... locations){
        InputStream in = this.getClass ().getClassLoader ().getResourceAsStream (locations[0].replace ("classpath:",""));
        try {
            config.load (in);
        } catch (IOException e) {
            e.printStackTrace ();
        }finally {
            if(null != in){
                try {
                    in.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
        doScanner (config.getProperty (SCAN_PACKAGE));
    }


    private void doScanner(String scanPackage){
        //转换文件路径，实际上就是把.替换为/
        URL url = this.getClass ().getClassLoader ().getResource ("/"+scanPackage.replaceAll("\\.","/"));
        File classPath = new File (url.getFile ());
        for(File file:classPath.listFiles ()){
            if(file.isDirectory ()){
                doScanner (scanPackage+"."+file.getName ());
            }else {
                if(!file.getName ().endsWith (".class")){
                    continue;
                }
                String className = (scanPackage+"."+file.getName ().replace (".class",""));
                registryBeanClasses.add (className);
            }
        }

    }

    public Properties getConfig(){
        return this.config;
    }

    public List<LRBeanDefinition> loadBeanDefinitions(){
        List<LRBeanDefinition> result = new ArrayList<> ();
            try {
                for(String className :registryBeanClasses){
                Class<?> beanClass = Class.forName (className);
                if(beanClass.isInterface ()){
                    continue;
                }
                result.add (doCreateBeanDefinitiion (toLowerFirstCase (beanClass.getSimpleName ()),beanClass.getName ()));
                Class<?>[] interfaces = beanClass.getInterfaces ();
                for(Class<?> i :interfaces){
                    result.add (doCreateBeanDefinitiion (i.getName (),beanClass.getName ()));
                }
                }
        }catch (Exception e) {
                e.printStackTrace ();
            }
            return result;
    }

    //把每一个配置信息解析成一个BeanDefinition
    private LRBeanDefinition doCreateBeanDefinitiion(String factoryBeanName,String beanClassName){
        LRBeanDefinition beanDefinition = new LRBeanDefinition ();
        beanDefinition.setBeanClassName (beanClassName);
        beanDefinition.setFactoryBeanName (factoryBeanName);
        return beanDefinition;
    }
    //将类名首字母改为大写
    private String toLowerFirstCase(String simpleName){
        char[] chars = simpleName.toCharArray ();
        //因为大小写的ASCII码相差32，而且小写比大写大32
        chars[0] += 32;
        return String.valueOf (chars);
    }

}
