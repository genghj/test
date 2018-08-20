package com.orange.springbootzookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RestapiApplication {
	@RequestMapping(name="HelloService",method= RequestMethod.GET,path="/hello")
	public String hello(){
		String value =null;
		try{
//			final ZooKeeper zk = new ZooKeeper(connectString:"192.168.33.47:2181");
		}catch (Exception e){
			e.printStackTrace();
		}

		return "hello";
	}
	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}
}
