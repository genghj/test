package com.youyuan.paystrategy.base.config.mybatis;

import java.util.Properties;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


/**
 * 注册MyBatis分页插件PageHelper
 */
@Configuration
public class MyBatisConfiguration {
//    @Bean
//    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Interceptor[] plugins =  new Interceptor[]{pageHelper()};
//        sqlSessionFactoryBean.setPlugins(plugins);
//        // 指定mybatisxml文件路径
//        sqlSessionFactoryBean.setMapperLocations(resolver
//                .getResources("classpath:/mybatis/*.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }
    //换了pagehelper-spring-boot-starter 后，需要删掉这个方法
//    @Bean
//    public PageHelper pageHelper() {
//       System.out.println("MyBatisConfiguration.pageHelper()");
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum", "true");
//        p.setProperty("rowBoundsWithCount", "true");
//        p.setProperty("reasonable", "true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }
   
}