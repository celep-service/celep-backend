package com.celeb._base.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    String jwtSchemeName = "jwtAuth";
    // API 요청헤더에 인증정보 포함
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
    // SecuritySchemes 등록
    Components components = new Components()
        .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
            .name(jwtSchemeName)
            .type(SecurityScheme.Type.HTTP) // HTTP 방식
            .scheme("bearer")
            .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)


    @Bean
    public OpenAPI openAPI() {

        Server prodServer = new Server().url("https://service-api.celep.shop");
        Server nowServer = new Server().url("/");

        return new OpenAPI()
            //.components(new Components())
            .addSecurityItem(securityRequirement)
            .components(components)
            .servers(Arrays.asList(nowServer, prodServer)) // 서버 URL 설정
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("celep Api")
            .description("celep Api 명세서")
            .version("1.0.0");
    }
}