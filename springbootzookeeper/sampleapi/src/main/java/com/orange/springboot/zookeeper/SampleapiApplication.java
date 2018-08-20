package com.orange.springboot.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "com")
@SpringBootApplication
public class SampleapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleapiApplication.class, args);
	}
}
