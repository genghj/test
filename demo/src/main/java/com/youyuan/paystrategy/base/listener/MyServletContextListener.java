package com.youyuan.paystrategy.base.listener;

import ch.qos.logback.classic.LoggerContext;
import com.youyuan.paystrategy.base.config.SetProperties;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
 
/**
 * 使用@WebListener注解，实现ServletContextListener接口
 *
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Autowired
    SetProperties setProperties;
//    在spring boot 使用过程中可能会需要自定义个servletContextListener 并有可能需要注入spring管理的类，
// 这时候如果没有特殊的配置的话，可能就会注入失败，这是网上很多碰到的问题，
// 在这里博主要说明在1.4.0+版本，是不会出现这个问题的。
    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("ServletContex初始化");
        System.out.println("MyServletContextListener.contextInitialized()");
        AutowireCapableBeanFactory autowireCapableBeanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()).getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(this);
    }
         @Override
         public void contextDestroyed(ServletContextEvent arg0) {
                   System.out.println("ServletContex销毁");
         }

}