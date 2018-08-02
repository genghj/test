package com.youyuan.paystrategy.dao;

import javax.annotation.Resource;

import com.youyuan.paystrategy.bean.Hello;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * 使用JdbcTemplate操作数据库.
 * @author Administrator
 *
 */
@Repository
public class HelloDao {
   
    @Resource
    private JdbcTemplate jdbcTemplate;
   
    /**
     * 通过id获取Hello对象.
     * @param id
     * @return
     */
    public Hello getById(long id){
       String sql = "select *from Hello where id=?";
       RowMapper<Hello> rowMapper = new BeanPropertyRowMapper<Hello>(Hello.class);
       return jdbcTemplate.queryForObject(sql, rowMapper,id);
    }
    public void save(Hello Hello){
        String sql = "insert into Hello(name,date) values(?,?)";
        jdbcTemplate.update(sql, new Object[]{Hello.getName(),Hello.getDate()});
    }

    /**
     * 不指定数据源使用默认数据源
     * @return
     */
    public List<Hello> getList(){
        String sql = "select *from Hello";
        return (List<Hello>) jdbcTemplate.query(sql, new RowMapper<Hello>(){
            @Override
            public Hello mapRow(ResultSet rs, int rowNum) throws SQLException {
                Hello h = new Hello();
                h.setId(rs.getLong("id"));
                h.setName(rs.getString("name"));;
                return h;
            }
        });
    }

    /**
     * 指定数据源
     * 在对应的service进行指定;
     * @return
     * @author SHANHY
     * @create  2016年1月24日
     */
    public List<Hello> getListByDs1(){
        /*
         * 这张表示复制的主库到ds1的，在ds中并没有此表.
         * 需要自己自己进行复制，不然会报错：Table 'test1.Hello1' doesn't exist
         */
        String sql = "select *from Hello1";
        return (List<Hello>) jdbcTemplate.query(sql, new RowMapper<Hello>(){

            @Override
            public Hello mapRow(ResultSet rs, int rowNum) throws SQLException {
                Hello Hello = new Hello();
                Hello.setId(rs.getLong("id"));
                Hello.setName(rs.getString("name"));;
                return Hello;
            }

        });
    }
}