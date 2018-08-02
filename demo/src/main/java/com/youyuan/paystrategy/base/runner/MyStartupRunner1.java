package com.youyuan.paystrategy.base.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 服务启动执行
 *实际应用中，我们会有在项目服务启动的时候就去加载一些数据或做一些事情这样的需求。
 为了解决这样的问题，Spring Boot 为我们提供了一个方法，通过实现接口 CommandLineRunner 来实现
 程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法。
 也可以利用@Order注解（或者实现Order接口）来规定所有CommandLineRunner实例的运行顺序
  */
@Component
@Order(value=1)
public class MyStartupRunner1 implements CommandLineRunner ,ApplicationRunner{
 
    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.asList(args));
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作1111 CommandLineRunner<<<<<<<<<<<<<");
    }
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println(Arrays.asList(applicationArguments));
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 1111ApplicationRunner<<<<<<<<<<<<<");
    }
}