package com.youyuan.paystrategy.controller;

import java.util.List;

import com.youyuan.paystrategy.bean.Demo;
import com.youyuan.paystrategy.bean.DemoInfo;
import com.youyuan.paystrategy.bean.Girl;
import com.youyuan.paystrategy.repository.DemoInfoRepository;
import com.youyuan.paystrategy.service.DemoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
/**
 *
 * @author Angel --守护天使
 * @version v.0.1
 * @date 2016年8月18日下午8:49:35
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DemoInfoRepository demoInfoRepository;
    @Autowired
    private DemoService demoService;

    @RequestMapping("/demoSave")
    public Demo demoSave(){
        Girl g = new Girl();
        g.hashCode();
        Demo demo = new Demo();
        demo.setName("张三");
        demoService.save(demo);
        return demo;
    }


    @RequestMapping("find2")
    public List<DemoInfo> find2(){
        return mongoTemplate.findAll(DemoInfo.class);
    }
    @RequestMapping("save")
    public String save(){
       DemoInfo demoInfo = new DemoInfo();
       demoInfo.setName("张三");
       demoInfo.setAge(20);
       demoInfoRepository.save(demoInfo);
      
       demoInfo = new DemoInfo();
       demoInfo.setName("李四");
       demoInfo.setAge(30);
       demoInfoRepository.save(demoInfo);
      
       return "ok";
    }
   
    @RequestMapping("find")
    public List<DemoInfo> find(){
       return demoInfoRepository.findAll();
    }
   
    @RequestMapping("findByName")
    public DemoInfo findByName(){
       return demoInfoRepository.findByName("张三");
    }
   
}