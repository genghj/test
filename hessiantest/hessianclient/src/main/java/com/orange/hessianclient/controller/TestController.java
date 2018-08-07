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
        return strategyService.getInstanceInfoMap("1");
    }
}