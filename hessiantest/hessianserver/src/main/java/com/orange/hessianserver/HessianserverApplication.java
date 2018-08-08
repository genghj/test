package com.orange.hessianserver;
import com.orange.hessianserver.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.caucho.HessianServiceExporter;
@ComponentScan(basePackages={"lover2","com"})
@SpringBootApplication
public class HessianserverApplication extends SpringBootServletInitializer {

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
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
	public static void main(String[] args) {
		SpringApplication.run(HessianserverApplication.class, args);
	}
}
