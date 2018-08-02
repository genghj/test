package com.orange.feignconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@EnableHystrixDashboard//Hystrix Dashboard (断路器：Hystrix 仪表盘
public class FeignconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignconsumerApplication.class, args);
	}
}
