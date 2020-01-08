package com.lr.spring.framework.common;/**
 * @Auther: 45417
 * @Date: 2020/1/7 19:39
 * @Description:
 */

import com.lr.spring.framework.orm.QueryRule;

import javax.management.ObjectName;
import java.util.List;
import java.util.Map;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: Oprete
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/7 19:39
 * @version v1.0
 *
 */
public interface Oprate<T> {
    /**
     *
     * @param queryRule 查询条件
     * @return
     * @throws Exception
     */
    List<T> select(QueryRule queryRule) throws Exception;

    /**
     *
     * @param queryRule 查询条件
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return
     * @throws Exception
     */
    Page<?> select(QueryRule queryRule,int pageNo,int pageSize)throws Exception;

    /**
     * 根据sql获取列表
     * @param sql sql语句
     * @param args 参数
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectBySql(String sql,Object... args) throws Exception;

    /**
     * 根据sql获取分页
     * @param sql sql语句
     * @param param 参数
     * @param pageNo 页码
     * @param pageSize 每页的个数
     * @return
     */
    Page<Map<String, Object>> selectBySqlToPage(String sql,Object [] param,int pageNo,int pageSize) throws Exception;

    /**
     * 删除一条数据
     * @param entity entity中的id不能为空，如果id为空，其他条件不能为空，都为空则不予执行
     * @return
     * @throws Exception
     */
    boolean delete(T entity) throws Exception;

    /**
     * 批量删除
     * @param list
     * @return 返回受影响的行数
     * @throws Exception
     */
    int deleteAll(List<T> list) throws Exception;

    /**
     * 插入一条数据
     * @param entity
     * @return
     * @throws Exception
     */
    int insertAndReturnId(T entity) throws Exception;

    /**
     * 插入一条数据
     * @param entity
     * @return
     * @throws Exception
     */
    boolean insert(T entity) throws Exception;

    /**
     * 批量数据
     * @param list
     * @return
     * @throws Exception
     */
    int insertAll(List<T> list) throws Exception;


    /**
     * 修改一条数据
     * @param entity
     * @return
     * @throws Exception
     */
    boolean update(T entity) throws Exception;
}
