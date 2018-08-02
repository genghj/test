package com.youyuan.paystrategy.controller;

import java.util.Map;
   
import org.springframework.beans.factory.annotation.Value;  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
   
/** 
 * 测试 
 * @author Angel 
 * @version v.0.1 
 */  
@Controller
@RequestMapping("/jsp")
public class JspController {  
        
       // 从 application.properties 中读取配置，如取不到默认值为Hello Angel
    @Value("${application.hello:Hello Angel}")  
    private String hello;  
     
        
       @RequestMapping("/helloJsp")  
       public String helloJsp(Map<String,Object> map){  
              System.out.println("HelloController.helloJsp().hello="+hello);  
              map.put("hello", hello);  
              return "helloJsp";
       }  
}  