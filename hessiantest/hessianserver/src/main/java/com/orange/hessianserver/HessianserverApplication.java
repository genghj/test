package com.orange.hessianserver;
import com.orange.hessianserver.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;

@SpringBootApplication
public class HessianserverApplication {

	@Autowired
	private StrategyService strategyService;

	//发布服务
	@Bean(name = "/strategyService")
	public HessianServiceExporter strategyService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(strategyService);
		exporter.setServiceInterface(StrategyService.class);
		return exporter;
	}

	public static void main(String[] args) {
		SpringApplication.run(HessianserverApplication.class, args);
	}
}
