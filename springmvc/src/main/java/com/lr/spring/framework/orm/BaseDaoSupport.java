package com.lr.spring.framework.orm;


import com.lr.spring.framework.common.jdbc.BaseDao;
import com.lr.spring.framework.util.GenericsUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: BaseDaoSupport
 * @Description: 对jdbcTemplate的包装
 * @Author: 45417
 * @Date: 2020/1/10 9:00
 * @version v1.0
 *BasoDao扩展类，主要功能是支持自动拼装sql语句，必须继承方可使用
 */
public abstract class BaseDaoSupport  <T extends Serializable,PK extends Serializable> implements BaseDao<T,PK> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoSupport.class);

    private String tableName = "";
    private JdbcTemplate jdbcTemplateWrite;
    private JdbcTemplate jdbcTemplateReadOnly;

    private DataSource dataSourceReadOnly;
    private DataSource dataSourceWrite;

    public EntityOperation<T> op;

    protected BaseDaoSupport(){
        Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass(),0);
        try {
            op = new EntityOperation<T>(entityClass,this.getPKColumn());
            this.setTableName(op.tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String getPKColumn();
    protected String getTableName(){
        return tableName;
    }

    protected DataSource getDataSourceReadOnly(){
        return dataSourceReadOnly;
    }

    protected DataSource getDataSourceWrite(){
        return dataSourceWrite;
    }

    protected void setTableName(String tableName){
        if(StringUtils.isEmpty(tableName)){
            this.tableName = op.tableName;
        }else {
            this.tableName = tableName;
        }
    }
    protected void setDataSourceWrite(DataSource dataSourceWrite){
        this.dataSourceWrite = dataSourceWrite;
        jdbcTemplateWrite = new JdbcTemplate(dataSourceWrite);
    }

    protected void setJdbcTemplateReadOnly(DataSource dataSourceReadOnly){
        this.dataSourceReadOnly = dataSourceReadOnly;
        jdbcTemplateReadOnly = new JdbcTemplate(dataSourceReadOnly);
    }

    protected JdbcTemplate getJdbcTemplateReadOnly(){
        return this.jdbcTemplateReadOnly;
    }

    protected JdbcTemplate getJdbcTemplateWrite(){
        return this.jdbcTemplateWrite;
    }

    /**
     * 还原默认列表
     */
    protected void restoreTableName(){
        this.setTableName(op.tableName);
    }

    protected abstract void setDataSource(DataSource dataSource);

    protected abstract <T> Object doLoad(String tableName, String pkColumn, Object pkValue, RowMapper<T> rowMapper);

    protected abstract boolean doInsert(Map parse);
}
