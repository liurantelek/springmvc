package com.lr.spring.framework.common;/**
 * @Auther: 45417
 * @Date: 2020/1/6 18:27
 * @Description:
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: Page
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/6 18:27
 * @version v1.0
 *
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 4049607231240912910L;

    private static final int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE;//每页的记录数

    private long start ;//当前页第一条数据在list中的位置

    private List<T> rows;//当前页存放的记录，类型一般为list

    private long total;//总记录数

    public Page(){
        this(DEFAULT_PAGE_SIZE,0,new ArrayList<T>(),0);
    }

    public Page(int pageSize, long start, List<T> rows, long total) {
        this.pageSize = pageSize;
        this.start = start;
        this.rows = rows;
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 取总页数
     * @return
     */
    public long getTotalPageCount(){
        if(total % pageSize ==0){
            return total/pageSize;
        }else {
           return total/pageSize+1;
        }
    }

    /**
     * 获取每页的数据容量
     * @return
     */
    public int getPageSize(){
        return pageSize;
    }

    /**
     * 获取当前页的记录
     * @return
     */
    public List<T> getRows(){
        return rows;
    }

    public void setRows(List<T> rows){
        this.rows = rows;
    }

    /**
     * 取当前页的当前页码，页码从1开始
     * @return
     */
    public long getPageNo(){
        return start/pageSize +1;
    }

    /**
     * 改页是否有下一页
     * @return
     */
    public boolean hasNextPage(){
        return this.getPageNo()<this.getTotalPageCount();
    }

    /**
     *
     * @return
     */
    public boolean hasPreviousPage(){
        return this.getPageNo()>1;
    }

    /**获取任意一页第一条数据在数据集中的位置，每页条数使用默认值
     *
     * @param pageNo
     * @return
     */
    protected static int getStartOfPage(int pageNo){
        return getStartOfPage(pageNo,DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任意一页第一条数据在数据集中的位置
     * @param pageNo 从1开始的页号
     * @param pageSize 每页的记录个数
     * @return 该页的第一条数据
     */
    private static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1)*pageSize;
    }

}
