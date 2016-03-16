package com.jpm.stockmarket.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ScopedProxyMode;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan(
		basePackages = {
				"com.jpm.stockmarket.domainmodel",
				"com.jpm.stockmarket.controller"
		},
		scopedProxy = ScopedProxyMode.INTERFACES
)
@Import({ PropertyPlaceholderConfig.class,
		      StockConfig.class, SwaggerConfig.class})
@SpringBootApplication
public class StocksWebApplication {

	public static void main(String[] args) throws Exception {

		if(args.length > 0 && StringUtils.isNotBlank(args[0])){
			System.setProperty("activeEnvironment",args[0]);
		}
		SpringApplication.run(StocksWebApplication.class, args);
	}

}
