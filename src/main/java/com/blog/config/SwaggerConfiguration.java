package com.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private ApiKey apiKey()
	{
		return new ApiKey("JWT",AUTHORIZATION_HEADER, "Header");
	}
	
	private List<SecurityContext> securityContext()
	{
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}
	
	private List<SecurityReference> securityReferences()
	{
		AuthorizationScope scopes = new AuthorizationScope("global","accessEveryThing");
		
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scopes}));
	}
	
	@Bean 
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiInfo getInfo() {
	
		return new ApiInfo("Blogging Application : Backend Course",
				"This Project is Developed By : Arun Yadav",
				"1.0", 
				"Terms of Services",
				new Contact("Arun Yadav","yadav9415arun@gmail.com", "http://localhost:8085/api"),
				"Licence Of API'S","API Licences URLS", Collections.emptyList());
	}

}
