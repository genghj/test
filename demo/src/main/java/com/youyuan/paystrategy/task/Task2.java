package com.youyuan.paystrategy.task;

import java.util.Date;
import java.util.Random;
 
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定义3个任务
 */
@Component//为了让@Async注解能够生效，还需要在Spring Boot的主程序中配置@EnableAsync
 public  class Task2 {
    //定义一个随机对象.
    public static Random random =new Random();
    //测试task1.

    //任务一;
    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一"+new Date());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }
 
    //任务二;
    @Async
    public  void doTaskTwo() throws Exception {
        System.out.println("开始做任务二"+new Date());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }
 
    //任务3;
    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三"+new Date());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }
 
}