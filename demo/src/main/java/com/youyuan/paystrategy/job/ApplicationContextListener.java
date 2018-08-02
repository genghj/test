package com.youyuan.paystrategy.job;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
 
//@WebListener
public class ApplicationContextListener implements ServletContextListener{
    private Scheduler scheduler = null;
   
    public void contextInitialized(ServletContextEvent arg0) {
       try {
           /*
            *在 Quartz 中， scheduler 由 scheduler 工厂创建：DirectSchedulerFactory 或者 StdSchedulerFactory。第二种工厂 StdSchedulerFactory 使用较多，
            *因为 DirectSchedulerFactory 使用起来不够方便，需要作许多详细的手工编码设置。
            */
           // 获取Scheduler实例
           scheduler = StdSchedulerFactory.getDefaultScheduler();
           scheduler.start();
           System.out.println("scheduler.start");
           System.out.println(scheduler.getSchedulerName());
          
           //具体任务.
           JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job1","group1").build();
          
           //触发时间点. (每5秒执行1次.)
//           SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
// 触发时间点
           CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ? *");

           Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group1").startNow().withSchedule(scheduleBuilder).build();
            // 交由Scheduler安排触发
           scheduler.scheduleJob(jobDetail,trigger);
       } catch (SchedulerException e) {
           e.printStackTrace();
       }
    }
   
    public void contextDestroyed(ServletContextEvent arg0) {
       try {
           scheduler.shutdown();
       } catch (SchedulerException e) {
           e.printStackTrace();
       }//关闭定时任务调度器.
    }
 
   
}