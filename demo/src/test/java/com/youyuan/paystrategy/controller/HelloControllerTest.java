package com.youyuan.paystrategy.controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//// SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)

//// 指定我们SpringBoot工程的Application启动类
@SpringBootTest
///由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
//@WebAppConfiguration
@AutoConfigureMockMvc
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    /**
     * 方法解析：

     perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
     get:声明发送一个get请求的方法。MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables)：根据uri模板和uri变量值得到一个GET请求方式的。另外提供了其他的请求的方法，如：post、put、delete等。
     param：添加request的参数，如上面发送请求的时候带上了了pcode = root的参数。假如使用需要发送json数据格式的时将不能使用这种方式，可见后面被@ResponseBody注解参数的解决方法
     andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确（对返回的数据进行的判断）；
     andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；
     andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理（对返回的数据进行的判断）；
     * @throws Exception
     */
    @Test
    public void getHello() throws Exception {
        String responseString = mvc.perform( MockMvcRequestBuilders.get("/getHelloById")    //请求的url,请求的方法是get
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                .param("id","2")         //添加参数
        ).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void testHelloController() throws Exception{
        RequestBuilder request = null;
        //1. get 以下user列表，应该为空》

        //1、构建一个get请求.
        request = MockMvcRequestBuilders.get("/hellos");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("[]"))
        ;
        System.out.println("UserControllerTest.testUserController().get");

        // 2、post提交一个user
        request = MockMvcRequestBuilders.post("/hellos")
                .param("id","1")
                .param("name","林峰")
        ;


        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("success"));

        // 3、get获取user列表，应该有刚才插入的数据
        request = MockMvcRequestBuilders.get("/hellos");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("[{\"id\":1,\"name\":\"林峰\"}]"));


        // 4、put修改id为1的user
        request = MockMvcRequestBuilders.put("/hellos/1")
                .param("name", "林则徐") ;
        mvc.perform(request)
                .andExpect(content().string("success"));

        // 5、get一个id为1的user
        request = MockMvcRequestBuilders.get("/hellos/1");
        mvc.perform(request)
                .andExpect(content().string("{\"id\":1,\"name\":\"林则徐\"}"));



        // 6、del删除id为1的user
        request = MockMvcRequestBuilders.delete("/hellos/1");
        mvc.perform(request)
                .andExpect(content().string("success"));

        // 7、get查一下user列表，应该为空
        request = MockMvcRequestBuilders.get("/hellos");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }
}