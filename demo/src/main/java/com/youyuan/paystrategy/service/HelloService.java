package com.youyuan.paystrategy.service;

import javax.annotation.Resource;

import com.github.pagehelper.PageInfo;
import com.youyuan.paystrategy.base.config.datasource.dynamic.TargetDataSource;
import com.youyuan.paystrategy.bean.Hello;

import java.util.List;

/**
 * 提供Demo服务类.
 * @author Administrator
 *
 */
public interface HelloService {
    Hello save(Hello demo) ;
    Hello getById(long id);
    void saveStringToRedis(String key ,String value);
    void deleteFromCache(long id);
      List<Hello> getList() ;
      List<Hello> getListByDs1() ;
    List<Hello> likeName(String name, int pageNum, int pageSize);
  }