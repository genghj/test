package com.orange.feignconsumer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
@EnableHystrixDashboard//Hystrix Dashboard (断路器：Hystrix 仪表盘
public class FeignconsumerApplication {
	private static final Logger LOG = Logger.getLogger(FeignconsumerApplication.class.getName());


//	zipkin测试----start
	@RequestMapping("/feign_hi")
	public String info(){
		LOG.log(Level.INFO, "info is being called fign receiv 2");
		return restTemplate.getForObject("http://localhost:8764/zipkin_info",String.class);
	}
	@Autowired
	private RestTemplate restTemplate;
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	//	zipkin测试----end
	public static void main(String[] args) {
		SpringApplication.run(FeignconsumerApplication.class, args);
	}
}
