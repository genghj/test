package com.youyuan.paystrategy.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
 
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 6000) //1分钟失效
public class RedisSessionConfig {
 
}