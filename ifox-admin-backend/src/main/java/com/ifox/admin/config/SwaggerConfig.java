package com.ifox.admin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/4
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI config(@Value("${spring.application.name}") String applicationName) {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                                .scheme("bearer")))
                .info(new Info().title("ifoxhub " + applicationName + " Api Doc")
                        .description("ifoxhub")
                        .license(new License().name("Apache 2.0").url("http://www.ifoxhub.com")))
                ;
    }
}
