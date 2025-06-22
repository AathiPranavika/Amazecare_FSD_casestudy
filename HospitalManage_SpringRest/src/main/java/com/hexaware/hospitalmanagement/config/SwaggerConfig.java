package com.hexaware.hospitalmanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
	    final String securitySchemeName = "JWTAuthentication";

	    return new OpenAPI()
	            .info(new Info()
	                    .title("Hospital Management System API")
	                    .description("This API provides access to the Hospital Management System's functionalities including patient records, appointments, staff management, and more.")
	                    .version("1.0.0"))
	            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
	            .components(new Components()
	                    .addSecuritySchemes(securitySchemeName,
	                            new SecurityScheme()
	                                    .name(securitySchemeName)
	                                    .type(SecurityScheme.Type.HTTP)
	                                    .scheme("bearer")
	                                    .bearerFormat("JWT")));
	}

}