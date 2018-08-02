package com.youyuan.paystrategy.mapper;

import java.util.List;

import com.youyuan.paystrategy.bean.Hello;
import org.apache.ibatis.annotations.*;

//@Mapper  //可以使用@Mapper
public interface HelloMappper {
   
    @Select("select *from Hello where name like  concat('%',#{name},'%') order by id desc")
     List<Hello> likeName(@Param("name") String name);
   
//    @Select("select *from Hello where id = #{id}")
//    @Select("<script> " +
//            "SELECT * " +
//            "from Hello " +
//            " <where> " +
//            "  1=1" +
//            " <if test=\"id != null\">and id=#{id}</if> " +
//            " </where> " +
//            " </script> ")
    @SelectProvider(type=HelloSqlProvider.class,method="select5")
    @Results({
            @Result(property="name",column="name")
    })
     Hello getById(long id);

    @Select("select name from Hello where id = #{id}")
     String getNameById(@Param("id") long id);
    @SelectProvider(type=HelloSqlProvider.class,method="select6")
     List<Hello> select6(Hello demo);
    /**
     * Options :
     * useCache=true，
     flushCache=false，
     resultSetType=FORWARD_ONLY，
     statementType=PREPARED，
     fetchSize= -1，timeout=-1 ，
     useGeneratedKeys=false ，
     keyProperty=”id“。
     * @param hello
     * @return
     */
    @Insert("insert into Hello(name,date) values(#{name},#{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
     long save(Hello hello);
   
}