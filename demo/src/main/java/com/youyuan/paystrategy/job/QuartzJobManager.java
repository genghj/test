package com.youyuan.paystrategy.job;

import org.jvnet.hk2.annotations.Service;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

@Service
public class QuartzJobManager {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    public Date addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cronExpression) {

        Scheduler scheduled =  schedulerFactoryBean.getScheduler();

        //job定义： // 任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

        //触发器构建
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
        try {
            return scheduled.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }
} 