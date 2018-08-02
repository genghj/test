package com.youyuan.paystrategy.service.impl;

import javax.annotation.Resource;

import com.youyuan.paystrategy.bean.UserInfo;
import com.youyuan.paystrategy.repository.UserInfoRepository;
import com.youyuan.paystrategy.service.UserInfoService;
import org.springframework.stereotype.Service;

   
@Service  
public class UserInfoServiceImpl implements UserInfoService {
     
    @Resource  
    private UserInfoRepository userInfoRepository;
     
    @Override  
    public UserInfo findByUsername(String username) {
       System.out.println("UserInfoServiceImpl.findByUsername()");  
       return userInfoRepository.findByUsername(username);  
    }  
     
}  