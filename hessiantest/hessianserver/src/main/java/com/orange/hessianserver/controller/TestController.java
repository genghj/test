package com.orange.hessianserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @RequestMapping("/test2")
    public String test2(String key,String value) {
       return key + "==="+value;
    }
}