package com.lr.spring.framework.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

/***
 * EntityOperaion主要实现数据库表结构和对象类结构的映射关系
 * @param <T>
 *     主要完成实体对象的反射操作
 */
public class EntityOperation<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityOperation.class);
    public Class<T> entityClass = null;//泛型实体的class对象
    public final Map<String,PropertyMapping> mappings;
    public final RowMapper<T> rowMapper;

    public final String tableName;

    public String allColumn = "*";
    public Field pkField;

    public EntityOperation(Class<T> clazz,String pk)throws Exception{
        if(!clazz.isAnnotationPresent(Entity.class)){
            throw new Exception("在"+clazz.getName()+"中沒有找到Entity注解，不能再ORM映射");
        }
        this.entityClass = clazz;
        Table table = entityClass.getAnnotation(Table.class);
        if(table != null){
            this.tableName = table.name();
        }else {
            this.tableName = entityClass.getSimpleName();
        }
        Map<String, Method> getters = ClassMappings.findPublicGetters(entityClass);
        Map<String, Method> setters = ClassMappings.findPublicSetters(entityClass);
        Field[] fields = ClassMappings.findFields(entityClass);
        fillPkFieldAndAllColumn(pk,fields);
        this.mappings = getPropertyMappings(getters,setters,fields);
        this.allColumn = this.mappings.keySet().toString().replace("[","").replace("]","").replaceAll(" ","");
        this.rowMapper = createRowMapper();
    }

    private RowMapper<T> createRowMapper() {
    }

    private Map<String, PropertyMapping> getPropertyMappings(Map<String, Method> getters, Map<String, Method> setters, Field[] fields) {
        Map<String,PropertyMapping> mappings = new Hashtable<>();
        String name;
        for(Field field:fields){
            if(field.isAnnotationPresent(Transient.class)){
                continue;
            }
            name = field.getName();
            if(name.startsWith("is")){
                name = name.substring(2);
            }
            name = Character.toLowerCase(name.charAt(0))+name.substring(1);
            Method setter = setters.get(name);
            Method getter = getters.get(name);
            if(setter ==null || getter ==null){
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            if(column == null){
                mappings.put(field.getName(),new PropertyMapping(getter,setter,field));
            }
        }
    }

    private void fillPkFieldAndAllColumn(String pk, Field[] fields) {
    }


    class PropertyMapping{

        final boolean

    }
}
