package com.youyuan.paystrategy.mapper;

import com.youyuan.paystrategy.bean.Hello;
import org.apache.ibatis.jdbc.SQL;

public class HelloSqlProvider {
     
    /** 
     * 查询语句. 
     * @param demo 
     * @return 
     */  
    public String select5(Hello demo){
       StringBuffer sql = new StringBuffer("select *from hello where 1=1 ");
       if(demo.getName() != null){  
           sql.append(" and name=#{name}");  
       }  
       if(demo.getId() != 0){
           sql.append(" and id=#{id}");
       }  
       return sql.toString();  
    }
    /**
     * 查询语句.使用SQL
     * @param demo
     * @return
     */
    public String select6(final Hello demo){
        return new SQL(){{
            SELECT("id,name");
            FROM("hello");
            if(demo.getName() != null){
                WHERE("name=#{name}");
            }
            if(demo.getId() != 0){
                WHERE("id=#{id}");
            }
        }}.toString();
    }
} 