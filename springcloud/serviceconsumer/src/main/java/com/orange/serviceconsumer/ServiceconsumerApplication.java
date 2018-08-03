package com.orange.serviceconsumer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient//EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard//Hystrix Dashboard (断路器：Hystrix 仪表盘
@RestController
public class ServiceconsumerApplication {
	private static final Logger LOG = Logger.getLogger(ServiceconsumerApplication.class.getName());

	@Bean
	@LoadBalanced//测试zipkin的时候需要注释
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(ServiceconsumerApplication.class, args);
	}


	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	//	zipkin测试----start
	@RequestMapping("/zipkin_hi")
	public String callHome(){
		LOG.log(Level.INFO, "calling trace service-hi serviceconsuer_hi 1");
		return restTemplate.getForObject("http://localhost:8765/feign_hi", String.class);
	}
	@RequestMapping("/zipkin_info")
	public String info(){
		LOG.log(Level.INFO, "calling trace service-hi info 3");
		return "i'm service-hi";
	}
	//	zipkin测试----end
}
