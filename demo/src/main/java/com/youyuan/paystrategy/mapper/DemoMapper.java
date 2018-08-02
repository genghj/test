package com.youyuan.paystrategy.mapper;

import com.youyuan.paystrategy.bean.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DemoMapper {
//      很容易把这里的rs当做返回的是Demo的id，实际含义是：
//      insert返回的只是insert方法的情况： 1成功 0失败。
      int save(Demo demo);
//      注意：在这里返回了int值，这个是值返回成功修改的数量，比如：成功找到了5条数据，并且修改成功了，那么返回值就是5。
        int update(@Param("id")int id, @Param("name")String name);
      int update1(@Param("id")int id, @Param("name")String name);
      int update2(@Param("id")int id, @Param("name")String name);

      //      int update(Demo demo);
      int delete(int id);
      List<Demo> selectAll();
      Demo selectById(int id);
      List<Demo> select1(Demo demo);
      List<Demo> select2(Demo demo);
      List<Demo> select3(Demo demo);
      /**foreach: 参数为array示例的写法*/
        List<Demo> select4(String[] ids);
      /**foreach: 数为list示例的写法*/
        List<Demo> select5(List<Integer> list);









}