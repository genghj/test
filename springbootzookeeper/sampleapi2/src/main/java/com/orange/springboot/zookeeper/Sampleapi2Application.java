package com.orange.springboot.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "com")
@SpringBootApplication
public class Sampleapi2Application {

	public static void main(String[] args) {
		SpringApplication.run(Sampleapi2Application.class, args);
	}
}
