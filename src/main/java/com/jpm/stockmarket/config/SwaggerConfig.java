package com.jpm.stockmarket.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import static com.google.common.base.Predicates.*;
import static springfox.documentation.builders.PathSelectors.*;

@Configuration
public class SwaggerConfig
{

	@Bean
	public Docket encryptionApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("stock-market-api")
				.apiInfo(apiInfo())
				.select()
				.paths(servicePaths())
				.build();
	}

	private Predicate<String> servicePaths() {
		return or(
				regex("/api/v1.0/.*"),
				regex("/show/.*")
		);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Stock Market API")
				.description("")
				.termsOfServiceUrl("#")
				.contact("Vaisakh Amolkunnumel")
				.license("")
				.licenseUrl("#")
				.version("2.0")
				.build();
	}
}