package com.youyuan.paystrategy.service;


import com.youyuan.paystrategy.bean.UserInfo;

public interface UserInfoService {
     
    /**通过username查找用户信息;*/  
    public UserInfo findByUsername(String username);
     
}