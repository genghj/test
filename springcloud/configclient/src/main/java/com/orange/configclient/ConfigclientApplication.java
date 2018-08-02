package com.orange.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@EnableEurekaClient
@RestController
@SpringBootApplication
public class ConfigclientApplication {
	@Value("${foo}")
	String foo;
	@RequestMapping(value = "/hi")
	public String hi(){
		System.out.println(foo);
		return foo;
	}
	public static void main(String[] args) {
		SpringApplication.run(ConfigclientApplication.class, args);
	}
}
