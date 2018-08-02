package com.youyuan.paystrategy.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 //// SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)

//// 指定我们SpringBoot工程的Application启动类
@SpringBootTest
///由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
@AutoConfigureMockMvc
public class ServiceTest {
    @Resource
    private HelloService helloService;
    @Autowired
    private MockMvc mvc;
     @Autowired
     protected WebApplicationContext wac;

     @Before()  //这个方法在每个方法执行之前都会执行一遍
     public void setup() {
         mvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
     }
    @Test
    public void xx() throws Exception {
        System.out.println("name:"+helloService.getById(2).getName());
//        Assert.assertEquals("hello",helloService.getById(2).getName());
    }
    @Test
    public void cc() throws Exception {
        String responseString = mvc.perform( MockMvcRequestBuilders.get("/getHelloById")    //请求的url,请求的方法是get
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                .param("id","2")         //添加参数
        ).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("--------返回的json = " + responseString);
    }
}