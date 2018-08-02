package com.youyuan.paystrategy.job;

import java.util.Date;
 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
/**
 * 任务类.
 * @author Angel --守护天使
 * @version v.0.1
 * @date 2017年4月21日
 */
public class HelloJob implements Job{
 
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 执行响应的任务.
       System.out.println("HelloJob.execute,"+new Date());
    }
   
}