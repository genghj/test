package com.youyuan.paystrategy;

import com.youyuan.paystrategy.base.config.jersey.JerseyConfig;
import com.youyuan.paystrategy.job.HelloJob;
import noneapplication.TestService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.jms.Topic;
import javax.servlet.MultipartConfigElement;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
//如果mapper类没有在Spring Boot主程序可以扫描的包或者子包下面，可以使用如下方式进行配置：
 @MapperScan("com.youyuan.paystrategy.mapper")
@ServletComponentScan
@SpringBootApplication//等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
//@ComponentScan(basePackages={"com"})//如果使用ComponentScan 就要加全，不加就不扫描
@EnableAsync//为了让@Async注解能够生效，还需要在Spring Boot的主程序中配置@EnableAsync
@EnableWebMvc
@EnableScheduling//启用任务调度.

public class Application extends SpringBootServletInitializer {//打war包必备
//	@Bean
//	public LocaleResolver localeResolver() {
//		SessionLocaleResolver slr = new SessionLocaleResolver();
//		//设置默认区域,
//		slr.setDefaultLocale(Locale.ENGLISH);
//		return slr;
//	}

    @Bean
    public Queue fooQueue() {
        return new Queue("foo");
    }

    @Bean
    public javax.jms.Queue queue() {
        return new ActiveMQQueue("sample.queue");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("sample.topic");
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        //设置默认区域,
        slr.setDefaultLocale(Locale.ENGLISH);
        slr.setCookieMaxAge(3600);//设置cookie有效期.
        return slr;
    }

    /**
     * 对上传的文件做一些限制
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("128KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("256KB");
        //Sets the directory location where files will be stored.
        //factory.setLocation("路径地址");
        return factory.createMultipartConfig();
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/jersey/*");
        // our rest resources will be available in the path /rest/*
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        /*
         * Banner.Mode.OFF:关闭;
         * Banner.Mode.CONSOLE:控制台输出，默认方式;
         * Banner.Mode.LOG:日志输出方式;
         */
//		application.setBannerMode(Banner.Mode.OFF);
        //获取context.
        ApplicationContext ctx = application.run(new String[]{"hello,", "林峰"});
//		SpringApplication.run(Application.class, new String[]{"hello,","林峰"});
        String[] beanNames = ctx.getBeanNamesForAnnotation(Service.class);
//		String[] beanNames =  ctx.getBeanDefinitionNames();
        System.out.println("所以beanNames个数：" + beanNames.length);
        for (String bn : beanNames) {
            System.out.println(bn);
        }
        //动态注入bean========start=====================================
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
//创建bean信息.
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TestService.class);
        beanDefinitionBuilder.addPropertyValue("name", "张三");
//动态注册bean.
        defaultListableBeanFactory.registerBeanDefinition("testService", beanDefinitionBuilder.getBeanDefinition());
//获取动态注册的bean.
        TestService testService = (TestService) ctx.getBean("testService");
//		TestService testService =ctx.getBean(TestService.class);//如果注册的bean名称不同，但是类型相同，按照类型获取，或报错，不知道选择哪个name
        //删除bean.
        testService.print();
        beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TestService.class);
        beanDefinitionBuilder.addPropertyValue("name", "李四");
//动态注册bean.
        defaultListableBeanFactory.registerBeanDefinition("testService1", beanDefinitionBuilder.getBeanDefinition());
        defaultListableBeanFactory.removeBeanDefinition("testService");
        try {
            testService = (TestService) ctx.getBean("testService");
            testService.print();
        } catch (Exception e) {
        }
        TestService ll = ctx.getBean(TestService.class);
        ll.print();
        //动态注入bean========end=====================================

        //---------定时任务quartz---------------------start--------
//        try {//非web工程方式，使用main进行测试
//          /*
//        *在 Quartz 中， scheduler 由 scheduler 工厂创建：DirectSchedulerFactory 或者 StdSchedulerFactory。第二种工厂 StdSchedulerFactory 使用较多，
//        *因为 DirectSchedulerFactory 使用起来不够方便，需要作许多详细的手工编码设置。
//        */
//            // 获取Scheduler实例
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.start();
//            System.out.println("scheduler.start");
//            System.out.println(scheduler.getSchedulerName());
//            //具体任务.
//            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();
//            //触发时间点. (每5秒执行1次.)
//            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
//            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().withSchedule(simpleScheduleBuilder).build();
//            // 交由Scheduler安排触发
//            scheduler.scheduleJob(jobDetail, trigger);
//            //睡眠20秒.
//            TimeUnit.SECONDS.sleep(20);
//            scheduler.shutdown();//关闭定时任务调度器.
//            System.out.println("scheduler.shutdown");
//        }catch (SchedulerException se){
//            System.out.println("scheduler SchedulerException :"+se.getMessage());
//        }catch (InterruptedException ie){
//            System.out.println("scheduler  InterruptedException:"+ie.getMessage());
//        }
        //---------定时任务quartz---------------------start--------
    }
}
