package com.youyuan.paystrategy.base.config.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
 
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
       register(RequestContextFilter.class);
       //配置restful package.
       packages("com.youyuan.paystrategy.jersey");
    }
}