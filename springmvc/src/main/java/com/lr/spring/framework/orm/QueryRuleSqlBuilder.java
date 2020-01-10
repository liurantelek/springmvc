package com.lr.spring.framework.orm;/**
 * @Auther: 45417
 * @Date: 2020/1/9 20:26
 * @Description:
 */

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: QueryRuleSqlBuilder
 * @Description: 通过用户构建的querybuilder来自动生成生成sql
 * @Author: 45417
 * @Date: 2020/1/9 20:26
 * @version v1.0
 *
 */
public class QueryRuleSqlBuilder {

    public int CURR_INDEX = 0;

    public List<String> properties;//保存列明列表

    public List<Object> values;//保存参数值列表

    public List<Order> orders;//保存排序规则列表

    public String whereSql = "";

    public String orderSql = "";
    public Object [] valueArr = new Object[]{};
    public Map<Object,Object> valueMap = new HashMap<>();

    /**
     * 获得查询条件
     * @return
     */
    public String getWhereSql() {
        return whereSql;
    }

    /**
     * 获得排序条件
     * @return
     */
    public String getOrderSql() {
        return orderSql;
    }

    /**
     * 获得参数值列表
     * @return
     */
    public Object[] getValues() {
        return valueArr;
    }

    /**
     * 获得参数列表
     * @return
     */
    public Map<Object, Object> getValueMap() {
        return valueMap;
    }
    
    public QueryRuleSqlBuilder(QueryRule queryRule){
        CURR_INDEX = 0;
        properties = new ArrayList<>();
        values = new ArrayList<>();
        orders = new ArrayList<>();
        for(QueryRule.Rule rule:queryRule.getRuleList()){
            switch (rule.getType()){
                case QueryRule.BETWEEN:
                    processBetween(rule);
                    break;
                case QueryRule.EQ:
                    processEqual(rule);
                    break;
                case QueryRule.LIKE:
                    processLike(rule);
                    break;
                case QueryRule.NOTEQ:
                    processNotEqual(rule);
                    break;
                case QueryRule.GT:
                    processGreaterThan(rule);
                    break;
                case QueryRule.GE:
                    processGreaterEqual(rule);
                    break;
                case QueryRule.LT:
                    processLessThen(rule);
                    break;
                case QueryRule.LE:
                    processLessEqual(rule);
                    break;
                case QueryRule.IN:
                    processIN(rule);
                    break;
                case QueryRule.NOTIN:
                    processNotIN(rule);
                    break;
                case QueryRule.ISNULL:
                     processIsNull(rule);
                    break;
                case QueryRule.ISNOTNULL:
                    processIsNotNull(rule);
                    break;
                case QueryRule.ISEMPTY:
                    processIsEmpty(rule);
                    break;
                case QueryRule.ISNOTEMPTY:
                    processIsNotEmpty(rule);
                    break;
                    case QueryRule.ASC_ORDER:
                    processOrder(rule);
                    break;
                case QueryRule.DESD_ORDER:
                    processOrder(rule);
                    break;
                    default:
                        throw  new IllegalArgumentException("type"+rule.getType()+" not supported.");
            }

        }
        //拼装where语句
        appendWhereSql();
        //拼装排序语句
        appendOrderSql();
        //拼装参数值
        appendValues();
    }

    /**
     * 去掉order
     * @param sql
     * @return
     */
    protected String removeOrders(String sql){
        Pattern p = Pattern.compile("order \\s*b by[\\w|\\w|\\s|\\s]*",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()){
            m.appendReplacement(sb,"");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 去掉select
     * @param sql
     * @return
     */
    protected String removeSelect(String sql){
        if(sql.toLowerCase().matches("from\\s+")){
            int beginPos = sql.toLowerCase().indexOf("from");
            return sql.substring(beginPos);
        }else {
            return sql;
        }
    }

    private void appendOrderSql() {
        StringBuffer orderSql = new StringBuffer();
        for(int i=0;i<orders.size();i++){
            if(i>0 && i<orders.size()){
                orderSql.append(",");
            }
            orderSql.append(orders.get(i).toString());
        }
        this.orderSql = removeSelect(removeOrders(orderSql.toString()));
    }

    private void appendValues() {
        Object[] val = new Object[]{values.size()};
        for(int i=0;i<values.size();i++){
            val[i] = values.get(i);
            valueMap.put(i,values.get(i));
            this.valueArr = val;
        }
    }

    /**
     * 拼装where语句
     */
    private void appendWhereSql() {
        StringBuffer whereSql = new StringBuffer();
        for(String p:properties){
            whereSql.append(p);
        }
        this.whereSql = removeSelect(removeOrders(whereSql.toString()));

    }

    /**
     * 拼装排序语句
     * @param rule
     */
    private void processOrder(QueryRule.Rule rule) {
        switch (rule.getType()){
            case QueryRule.ASC_ORDER:
                if(!StringUtils.isEmpty(rule.getProperty_name())){
                    orders.add(Order.asc(rule.getProperty_name()));
                }
                break;
            case QueryRule.DESD_ORDER:
                if(!StringUtils.isEmpty(rule.getProperty_name())){
                    orders.add(Order.desc(rule.getProperty_name()));
                }
                break;
                default:
                    break;
        }

    }

    private void processIsNotEmpty(QueryRule.Rule rule) {
        add(rule.getAndOr(),rule.getProperty_name(),"<>","''");
    }

    private void processIsEmpty(QueryRule.Rule rule) {
        add(rule.getAndOr(),rule.getProperty_name(),"=","''");
    }

    private void processIsNotNull(QueryRule.Rule rule) {
        add(rule.getAndOr(),rule.getProperty_name(),"is not null",null);
    }

    /**
     * 处理 isnull
     * @param rule
     */
    private void processIsNull(QueryRule.Rule rule) {
        add(rule.getAndOr(),rule.getProperty_name(),"is null",null);
    }

    /**
     * 处理 notin
     * @param rule
     */
    private void processNotIN(QueryRule.Rule rule) {
        processInAndNotIn(rule,"not in");
    }

    /**
     * 处理 in
     * @param rule
     */
    private void processIN(QueryRule.Rule rule) {
        processInAndNotIn(rule,"in");
    }

    private void processInAndNotIn(QueryRule.Rule rule,String name){
        if(ArrayUtils.isEmpty(rule.getValues())){
            return;
        }
        if((rule.getValues().length == 1)&& (rule.getValues()[0] !=null)&& (rule.getValues()[0] instanceof List)){
            List<Object> list = (List<Object>) rule.getValues()[0];
            if(list != null && list.size()>0){
                for(int i=0;i<list.size();i++){
                    if(i ==0 && i ==list.size()-1){
                        add(rule.getAndOr(),rule.getProperty_name(),"",name+"(",list.get(i),")");
                    }else if(i ==0 && i<list.size()-1){
                        add(rule.getAndOr(),rule.getProperty_name(),"",name+"(",list.get(i),"");
                    }
                    if(i>0 && i<list.size()-1){
                        add(0,"","","",list.get(i),"");
                    }
                    if(i == list.size()-1 && list.size() !=0){
                        add(0,"","","",list.get(i),")");
                    }
                }
            }
        }else {
            Object[] list = rule.getValues();
            for(int i=0;i<list.length;i++){
                if(i ==0 && i ==list.length-1){
                    add(rule.getAndOr(),rule.getProperty_name(),"",name+"(",list[i],")");
                }else if(i ==0 && i<list.length-1){
                    add(rule.getAndOr(),rule.getProperty_name(),"",name+"(",list[i],"");
                }
                if(i>0 && i<list.length-1){
                    add(0,"","","",list[i],"");
                }
                if(i == list.length-1 && list.length !=0){
                    add(0,"","","",list[i],")");
                }
            }
        }
    }

    /**
     * 处理 <=
     * @param rule
     */
    private void processLessEqual(QueryRule.Rule rule) {
        if(ArrayUtils.isEmpty(rule.getValues())){
            return;
        }
        add(rule.getAndOr(),rule.getProperty_name(),"<=",rule.getValues()[0]);
    }

    /**
     * 处理 <
     * @param rule
     */
    private void processLessThen(QueryRule.Rule rule) {
        if(ArrayUtils.isEmpty(rule.getValues())){
            return;
        }
        add(rule.getAndOr(),rule.getProperty_name(),"<",rule.getValues()[0]);
    }

    /**
     * 处理 >=
     * @param rule
     */
    private void processGreaterEqual(QueryRule.Rule rule) {
        if(ArrayUtils.isEmpty(rule.getValues())){
            return;
        }
        add(rule.getAndOr(),rule.getProperty_name(),">=",rule.getValues()[0]);
    }

    /**
     * 处理 >
     * @param rule
     */
    private void processGreaterThan(QueryRule.Rule rule) {
        if(ArrayUtils.isEmpty(rule.getValues())){
            return;
        }
        add(rule.getAndOr(),rule.getProperty_name(),">",rule.getValues()[0]);
    }

    private void processNotEqual(QueryRule.Rule rule) {
        if(ArrayUtils.isEmpty(rule.getValues())){
            return;
        }
        add(rule.getAndOr(),rule.getProperty_name(),"<>",rule.getValues()[0]);
    }

    private void processLike(QueryRule.Rule rule) {
        if((ArrayUtils.isEmpty(rule.getValues()))){
            return;
        }
        Object obj = rule.getValues()[0];
        if(obj != null){
            String value = obj.toString();
            if(!StringUtils.isEmpty(value)){
                value = value.replace("*","%");
                obj = value;
            }
        }
        add(rule.getAndOr(),rule.getProperty_name(),"like","%"+rule.getValues()[0]+"%");
    }

    /**
     * 处理等于 =
     * @param rule
     */
    private void processEqual(QueryRule.Rule rule) {
        if((ArrayUtils.isEmpty(rule.getValues()))){
            return;
        }
        add(rule.getAndOr(),rule.getProperty_name(),"=",rule.getValues()[0]);
    }

    /**
     * 处理between
     * @param rule
     */
    private void processBetween(QueryRule.Rule rule) {
        if((ArrayUtils.isEmpty(rule.getValues()))){
            return;
        }
        add(rule.getAndOr(),rule.getProperty_name(),"=",rule.getValues()[0]);
    }

    private void add(int andOr, String key, String split, Object value) {
        add(andOr,key,split,"",value,"");
    }
    private void  add(int andOr,String key,String split,String prefix,Object value,String suffix){
        String andOrStr = ( 0 == andOr? "": (QueryRule.AND == andOr)? "and": "or");
        properties.add(CURR_INDEX,andOrStr+key+" "+split+prefix+(null!=value?"？":" ")+suffix);
        if(null!=value){
            values.add(CURR_INDEX);
            CURR_INDEX++;
        }
    }


}
