package com.lr.spring.framework.orm;/**
 * @Auther: 45417
 * @Date: 2020/1/6 18:47
 * @Description:
 */

import com.sun.javafx.css.Rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: QueryRule
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2020/1/6 18:47
 * @version v1.0
 *主要用于构造查询条件
 */
public final class QueryRule implements Serializable {
    private static final long serialVersionUID = 7478488057335618828L;

    private static final int ASC_ORDER = 101;
    private static final int DESD_ORDER = 102;
    private static final int LIKE = 1;
    private static final int IN = 2;
    private static final int NOTIN = 3;
    private static final int BETWEEN = 4;
    private static final int EQ = 5;
    private static final int NOTEQ = 6;
    private static final int GT = 7;
    private static final int GE = 8;
    private static final int LT = 9;
    private static final int LE = 10;
    private static final int ISNULL = 11;
    private static final int ISNOTNULL = 12;
    private static final int ISEMPTY = 13;
    private static final int ISNOTEMPTY = 14;
    private static final int AND = 201;
    private static final int OR = 202;

    private List<Rule> ruleList = new ArrayList<>();
    private List<QueryRule> queryRuleList = new ArrayList<>();
    private String propertyName;

    private QueryRule(){}

    private QueryRule(String propertyName){
        this.propertyName = propertyName;
    }

    public static QueryRule getInstance(){
        return new QueryRule();
    }

    /**
     * 添加升序规则
     * @param propertyName
     * @return
     */
    public QueryRule addAscOrder(String propertyName){
        this.ruleList.add(new Rule(ASC_ORDER,propertyName));
        return this;
    }

    /**
     * 降序规则
     * @param propertyName
     * @return
     */
    public QueryRule addDescOrder(String propertyName){
        this.ruleList.add(new Rule(DESD_ORDER,propertyName));
        return this;
    }

    public QueryRule addIsNull(String propertyName){
        this.ruleList.add(new Rule(ISNULL,propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule addIsNotNull(String propertyName){
        this.ruleList.add(new Rule(ISNOTNULL,propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule addIsEmpty(String propertyName){
        this.ruleList.add(new Rule(ISEMPTY,propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule addIsNotEmpty(String propertyName){
        this.ruleList.add(new Rule(ISNOTEMPTY,propertyName).setAndOr(AND));
        return this;
    }

    public QueryRule andLike(String propertyName,Object value){
        this.ruleList.add(new Rule(LIKE,propertyName,new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andEqual(String propertyName,Object value){
        this.ruleList.add(new Rule(EQ,propertyName,new Object[]{value}).setAndOr(AND));
        return this;
    }

    public QueryRule andBetween(String propertyName,Object... values){
        this.ruleList.add(new Rule(BETWEEN,propertyName,values).setAndOr(AND));
        return this;
    }

    public QueryRule andIn(String propertyName,List<Object> values){
        this.ruleList.add(new Rule(IN,propertyName,new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule andIn(String propertyName,Object... values){
        this.ruleList.add(new Rule(IN,propertyName,values).setAndOr(AND));
        return this;
    }

    public QueryRule andNotIn(String propertyName,Object... values){
        this.ruleList.add(new Rule(NOTIN,propertyName,values).setAndOr(OR));
        return this;
    }

    public QueryRule andNotIn(String propertyName,List<Object> values){
        this.ruleList.add(new Rule(NOTIN,propertyName,new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule andNotEqual(String propertyName,Object values){
        this.ruleList.add(new Rule(NOTEQ,propertyName,new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule andGreaterThan(String propertyName,Object values){
        this.ruleList.add(new Rule(GT,propertyName,new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule andGreaterEqual(String propertyName,Object values){
        this.ruleList.add(new Rule(GE,propertyName,new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule andLessThan(String propertyName,Object values){
        this.ruleList.add(new Rule(LT,propertyName,new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule andLessEqual(String propertyName,Object values){
        this.ruleList.add(new Rule(LE,propertyName,new Object[]{values}).setAndOr(AND));
        return this;
    }

    public QueryRule orIsNull(String propertyName){
        this.ruleList.add(new Rule(ISNULL,propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orIsNotNull(String propertyName){
        this.ruleList.add(new Rule(ISNOTNULL,propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orIsEmpty(String propertyName){
        this.ruleList.add(new Rule(ISEMPTY,propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orIsNotEmpty(String propertyName){
        this.ruleList.add(new Rule(ISNOTEMPTY,propertyName).setAndOr(OR));
        return this;
    }

    public QueryRule orLike(String propertyName,Object value){
        this.ruleList.add(new Rule(LIKE,propertyName,new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orEqual(String propertyName,Object value){
        this.ruleList.add(new Rule(EQ,propertyName,new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orBetween(String propertyName,Object... value){
        this.ruleList.add(new Rule(BETWEEN,propertyName,value).setAndOr(OR));
        return this;
    }

    public QueryRule orIn(String propertyName,List<Object> values){
        this.ruleList.add(new Rule(IN,propertyName,new Object[]{values}).setAndOr(OR));
        return this;
    }

    public QueryRule orIn(String propertyName,Object... value){
        this.ruleList.add(new Rule(IN,propertyName,value).setAndOr(OR));
        return this;
    }

    public QueryRule orNotEqual(String propertyName,Object value){
        this.ruleList.add(new Rule(NOTEQ,propertyName,new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orGreateThan(String propertyName,Object value){
        this.ruleList.add(new Rule(GT,propertyName,new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orGreateEqual(String propertyName,Object value){
        this.ruleList.add(new Rule(GE,propertyName,new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orLessThan(String propertyName,Object value){
        this.ruleList.add(new Rule(LT,propertyName,new Object[]{value}).setAndOr(OR));
        return this;
    }

    public QueryRule orLessEqual(String propertyName,Object value){
        this.ruleList.add(new Rule(LE,propertyName,new Object[]{value}).setAndOr(OR));
        return this;
    }

    public List<Rule> getRuleList(){
        return this.ruleList;
    }

    public List<QueryRule> getQueryRuleList(){
        return this.queryRuleList;
    }

    public String getPropertyName(){
        return this.propertyName;
    }


    protected class Rule implements Serializable{

        private static final long serialVersionUID = 3069945308047820387L;

        private int type ;//规则的类型
        private String property_name;
        private Object[] values;
        private int andOr = AND;

        public Rule(int paramInt,String paramString){
            this.property_name = paramString;
            this.type = paramInt;
        }

        public Rule(int paramInt,String paramString,Object[] paramArrayOfObject){
            this.property_name = paramString;
            this.values = paramArrayOfObject;
            this.type = paramInt;
        }

        public int getType() {
            return type;
        }

        public Rule setAndOr(int andOr) {
            this.andOr = andOr;
            return this;
        }

        public String getProperty_name() {
            return property_name;
        }

        public Object[] getValues() {
            return values;
        }

        public int getAndOr() {
            return andOr;
        }

    }

}
