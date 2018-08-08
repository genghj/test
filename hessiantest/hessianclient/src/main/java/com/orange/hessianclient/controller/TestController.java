package com.orange.hessianclient.controller;

import com.alibaba.fastjson.JSON;
import com.orange.hessianserver.service.client.HClient;
import com.orange.hessianserver.service.StrategyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    private StrategyService strategyService = HClient.getService(StrategyService.class);

    @RequestMapping("/test2")
    public Map test2(String key,String value) {
        System.out.println(JSON.toJSONString(strategyService.setInstanceInfoMap(key,value)));
        long userId = Math.round(Math.random()*1000000);
        long channel  =Math.round(Math.random()*5000);
        long areaCode  =Math.round(Math.random()*350);
        return   strategyService.getStrategyInfoMap("zhiFu", "douBi",  userId,  "0" ,   channel+"",  ""+ areaCode);
//        return strategyService.getInstanceInfoMap("1");
    }
}