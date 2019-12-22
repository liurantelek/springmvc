package com.lr.spring.framework.context;

import com.lr.spring.framework.beans.LRBeanWrapper;
import com.lr.spring.framework.beans.config.LRBeanDefinition;
import com.lr.spring.framework.beans.config.LRBeanPostProcessor;
import com.lr.spring.framework.beans.support.LRBeanDefinitionReader;
import com.lr.spring.framework.beans.support.LRDefaultListableBeanFactory;
import com.lr.spring.framework.core.LRBeanFactory;
import com.lr.spring.framework.webmvc.annotation.LRAutowired;
import com.lr.spring.framework.webmvc.annotation.LRController;
import com.lr.spring.framework.webmvc.annotation.LRService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuran
 * @create 2019-12-21-11:00
 */
public class LRApplicationContext extends LRDefaultListableBeanFactory implements LRBeanFactory {

   private String[] configLoactions;

   private LRBeanDefinitionReader reader;
   //单例的ioc容器缓存
   private Map<String,Object> factoryBeanObjectCache = new ConcurrentHashMap<> ();
    //通用的ioc容器
   private Map<String,LRBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<> ();

   public LRApplicationContext(String... configLoactions){
       this.configLoactions = configLoactions;
       try {
           refresh ();
       } catch (Exception e) {
           e.printStackTrace ();
       }
   }

    @Override
    public void refresh() throws Exception {
        //1、定位配置文件
        reader = new LRBeanDefinitionReader (this.configLoactions);
        //2、加载配置文件，扫描相关的类，把他们封装成BeanDefinition
        List<LRBeanDefinition> beanDefinitions = reader.loadBeanDefinitions ();
        //3、注册，把配置信息放到容器里面实现（伪Ioc容器）
        doRegisterBeanDefinition (beanDefinitions);
        //4、把不是延时加载的类提前初始化
        doAutowired ();
    }

    private void doAutowired(){
       for(Map.Entry<String,LRBeanDefinition> beanDefinitionEntry:super.beanDefinitionMap.entrySet ()){
           String beanName = beanDefinitionEntry.getKey ();
           if(!beanDefinitionEntry.getValue ().isLazyInit ()){
               try {
                   getBean (beanName);
               } catch (Exception e) {
                   e.printStackTrace ();
               }
           }
       }
    }

    private void doRegisterBeanDefinition(List<LRBeanDefinition> beanDefinitions)throws Exception{
       for(LRBeanDefinition beanDefinition:beanDefinitions){
           if(super.beanDefinitionMap.containsKey (beanDefinition.getFactoryBeanName ())){
               throw new Exception ("The"+beanDefinition.getFactoryBeanName ()+" is exists");
           }
           super.beanDefinitionMap.put (beanDefinition.getFactoryBeanName (),beanDefinition);
       }
       //容器初始化完成
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        LRBeanDefinition beanDefinition = super.beanDefinitionMap.get (beanName);

        LRBeanPostProcessor beanPostProcessor = null;
        try {
            beanPostProcessor = new LRBeanPostProcessor ();
            Object instance = instancetiateBean(beanDefinition);
            if(null == instance){
                return null;
            }

            beanPostProcessor.postProcessBeforeInitialization (instance,beanName);
            LRBeanWrapper beanWrapper = new LRBeanWrapper (instance);
            this.factoryBeanInstanceCache.put (beanName,beanWrapper);
            beanPostProcessor.postProcessAfterInitialization (instance,beanName);
            populateBean(beanName,instance);
            return this.factoryBeanInstanceCache.get (beanName).getWrappedInstance ();
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }



    }

    private void populateBean(String beanName, Object instance) {
        Class clazz = instance.getClass ();
        if(!(clazz.isAnnotationPresent (LRController.class)||clazz.isAnnotationPresent (LRService.class))){
            return;
        }
        Field[] fields = clazz.getDeclaredFields ();
        for(Field field :fields){
            if(!field.isAnnotationPresent (LRAutowired.class)){
                continue;
            }
            LRAutowired autowired = field.getAnnotation(LRAutowired.class);
            String autowiredBeanName = autowired.value ().trim ();
            if("".equals (autowiredBeanName)){
                autowiredBeanName = field.getType ().getName ();
            }
            field.setAccessible (true);
            try {
                field.set (instance,this.factoryBeanInstanceCache.get (autowiredBeanName).getWrappedInstance ());
            } catch (IllegalAccessException e) {
                e.printStackTrace ();
            }
        }
    }

    private Object instancetiateBean(LRBeanDefinition beanDefinition) {
       Object instance = null;
       String className = beanDefinition.getBeanClassName ();
        try {
            if(this.factoryBeanObjectCache.containsKey (className)){
                instance = this.factoryBeanObjectCache.get (className);
            }else {
                Class<?> clazz = Class.forName (className);
                instance = clazz.newInstance ();
                this.factoryBeanObjectCache.put (beanDefinition.getBeanClassName (),instance);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return instance;
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean (beanClass.getName ());
    }

    public String[] getBeanDefinitionNames(){
        return this.beanDefinitionMap.keySet ().toArray (new String[this.beanDefinitionMap.size ()]);
    }
    public int getBeanDefinitionCount(){
        return this.beanDefinitionMap.size ();
    }

    public Properties getConfig(){
        return this.reader.getConfig ();
    }
}
