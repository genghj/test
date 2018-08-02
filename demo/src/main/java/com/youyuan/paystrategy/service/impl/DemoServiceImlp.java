package com.youyuan.paystrategy.service.impl;

import com.youyuan.paystrategy.bean.Demo;
import com.youyuan.paystrategy.mapper.DemoMapper;
import com.youyuan.paystrategy.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service  
public class DemoServiceImlp implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @Transactional
    public void save(Demo demo){
        demoMapper.save(demo);
    }
}