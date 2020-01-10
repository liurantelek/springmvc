package com.lr.spring.framework.orm;

import com.lr.spring.framework.common.Page;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: BaseDaoImpl
 * @Description:
 * @Author: 45417
 * @Date: 2020/1/10 10:36
 * @version v1.0
 *
 */
@Component
public class BaseDaoImpl<T extends Serializable,PK extends Serializable> extends BaseDaoSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);

    @Override
    protected String getPKColumn() {
        return null;
    }

    @Override
    protected void setDataSource(DataSource dataSource) {

    }

    @Override
    protected boolean doInsert(Map parse) {
        return false;
    }


    @Override
    protected Object doLoad(String tableName, String pkColumn, Object pkValue, RowMapper rowMapper) {
        return null;
    }

    /**
     * 查询函数，使用查询规则
     * @param queryRule 查询条件
     * @return 查询结果的list
     * @throws Exception
     */
    @Override
    public List<T> select(QueryRule queryRule) throws Exception {
        QueryRuleSqlBuilder builder = new QueryRuleSqlBuilder(queryRule);
        String ws = removeFirstAnd(builder.getWhereSql());
        String whereSql = ("".equals(ws)?ws:(" where "+ws));
        String sql = "select"+op.allColumn+" from "+getTableName()+whereSql;
        Object[] values = builder.getValues();
        String orderSql = builder.getOrderSql();
        orderSql = (StringUtils.isEmpty(orderSql)?" ":(" order by "+orderSql));
        sql += orderSql;
        LOGGER.info(sql);
        return this.getJdbcTemplateReadOnly().query(sql,this.op.rowMapper,values);
    }


    /**
     * 分页查询函数，使用查询规则
     * @param queryRule 查询条件
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return
     * @throws Exception
     */
    @Override
    public Page<?> select(QueryRule queryRule, final int pageNo,final int pageSize) throws Exception {
        QueryRuleSqlBuilder builder = new QueryRuleSqlBuilder(queryRule);
        Object[] values = builder.getValues();
        String ws = removeFirstAnd(builder.getWhereSql());
        String whereSql = ("".equals(ws)?ws:(" where "+ws));
        String countSql = "select count(1) from "+getTableName()+whereSql;
        long count = (long)this.getJdbcTemplateReadOnly().queryForMap(countSql,values).get("count(1)");
        if(count ==0){
            return new Page<T>();
        }
        long start = (pageNo - 1)*pageSize;
        //在有数据的情况下，继续查询
        String orderSql = builder.getOrderSql();
        orderSql = (StringUtils.isEmpty(orderSql)?" ":(" order by "+orderSql));
        String sql = "select "+op.allColumn+" from "+getTableName()+whereSql+orderSql+" limit "+start+","+pageSize;
        List<T> list = this.getJdbcTemplateReadOnly().query(sql,this.op.rowMapper,values);
        LOGGER.info(sql);
        return new Page<>(pageSize,start,list,count);
    }

    private String removeFirstAnd(String whereSql) {
        return whereSql.replaceFirst("and","");
    }

    /**
     * 根据SQL语句进行查询，参数为object数据对象
     * @param sql sql语句
     * @param args 参数
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectBySql(String sql, Object... args) throws Exception {
        return this.getJdbcTemplateReadOnly().queryForList(sql,args);
    }

    /**
     * 分页查询特殊sql语句
     * @param sql sql语句
     * @param param 参数
     * @param pageNo  页码
     * @param pageSize 每页条数
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> selectBySqlToPage(String sql, Object[] param, final int pageNo,final int pageSize) throws Exception {
        String countSql = "select count(1) from ("+sql+") a";
        long count = (long)this.getJdbcTemplateReadOnly().queryForMap(countSql,param).get("count(1)");
        if(count == 0){
            return new Page<>();
        }
        long start = (pageNo -1)*pageSize;
        sql = sql+" limit "+start+","+pageSize;
        List<Map<String,Object>> list = this.getJdbcTemplateReadOnly().queryForList(sql,param);
        LOGGER.info(sql);
        return new Page<>(pageSize,start,list,count);
    }

    /***
     * 获取默认的实例对象
     * @param pkValue
     * @param rowMapper
     * @param <T>
     * @return
     */
    private <T> T doLoad(Object pkValue, RowMapper<T> rowMapper){
        Object obj = (Object) this.doLoad(getTableName(),getPKColumn(),pkValue,rowMapper);
        if(obj != null){
            return (T) obj;
        }
        return null;
    }

    @Override
    public boolean delete(Object entity) throws Exception {
        return false;
    }

    @Override
    public int deleteAll(List list) throws Exception {
        return 0;
    }

    @Override
    public PK insertAndReturnId(Object entity) throws Exception {
        return (PK)this.doInsertRuturnKey(op.parse(entity));
    }

    private Serializable doInsertRuturnKey(Map<String,Object> params) {
        final List<Object> values = new ArrayList<>();
        final String sql = makeSimpleInsertSql(getTableName(),params,values);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSourceWrite());
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for(int i=0;i<values.size();i++){
                    ps.setObject(i+1,values.get(i)==null?null:values.get(i));
                }
                return ps;
             }
        },keyHolder);
        if(keyHolder == null){
            return "";
        }
        Map<String,Object> keys = keyHolder.getKeys();
        if (keys == null ||keys.keySet() == null || keys.values().size() == 0) {
            return "";
        }

        Object key = keys.values().toArray()[0];
        if (key == null || !(key instanceof Serializable)) {
            Class clazz = key.getClass();
            return (clazz == int.class || clazz == Integer.class )?((Number)key).intValue():((Number)key).longValue();
        }else if(key instanceof  String){
            return (String)key;
        }else {
            return (Serializable) key;
        }
    }

    private String makeSimpleInsertSql(String tableName, Map<String, Object> params, List<Object> values) {
        return null;
    }

    @Override
    public boolean insert(Object entity) throws Exception {
        return  this.doInsert(op.parse(entity));
    }

    @Override
    public int insertAll(List list) throws Exception {
        int count = 0,len = list.size(),step = 50000;
        Map<String, EntityOperation.PropertyMapping> pm = op.mappings;
        int maxPage = (len % step == 0)?(len /step):(len/step+1);
        for(int i=0;i<= maxPage;i++){
            Page<T> page  = pagination(list,i,step);
            String sql = "insert into"+getTableName()+"("+op.allColumn+" )values";
            StringBuffer valstr = new StringBuffer();
            Object[] values = new Object[pm.size()*page.getRows().size()];
            for(int j=0;j<page.getRows().size();j++){
                if(j >0 && j<page.getRows().size()){
                    valstr.append(",");
                }
                valstr.append("(");
                int k = 0;
                for(EntityOperation.PropertyMapping p:pm.values()){
                    values[j*pm.size()+k] = p.getter.invoke(page.getRows().get(j));
                    if(k>0 &&k<pm.size()){
                        valstr.append(",");
                    }
                    valstr.append("?");
                    k++;
                }
                valstr.append(")");
            }
            int result = getJdbcTemplateWrite().update(sql+valstr.toString(),values);
            count += result;
        }
        return count;
    }

    private Page<T> pagination(List list, int i, int step) {
        return new Page<>();
    }

    @Override
    public boolean update(Object entity) throws Exception {
        return false;
    }
}
