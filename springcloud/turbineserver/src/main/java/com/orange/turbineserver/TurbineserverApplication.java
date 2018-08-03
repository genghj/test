package com.orange.turbineserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@SpringBootApplication
public class TurbineserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineserverApplication.class, args);
	}
}
