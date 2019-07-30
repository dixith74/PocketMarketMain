package com.pm.bs.swagger.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile("prod")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String API_PKG = "com.pm.bs";
    private static final Predicate<String> PATH_SELECTOR = PathSelectors.any();
    private static final Predicate<RequestHandler> HANDLER = RequestHandlerSelectors.basePackage(API_PKG);
    private static final DocumentationType DOC_TYPE = DocumentationType.SWAGGER_2;
    
    @Bean
    public Docket api() {
        return new Docket(DOC_TYPE).select().apis(HANDLER).paths(PATH_SELECTOR).build().apiInfo(metaData());
    }
    
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "POCKET MARKET APP",
                "REST API for Pocket market main module",
                "1.0",
                "Terms of service",
                new Contact("Pocket market", "https://pm.com/about/", "contact@pm.com"),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return builder;
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder bulder) {
    	return bulder.basicAuthentication("pm", "pm123").build();
    }
}
