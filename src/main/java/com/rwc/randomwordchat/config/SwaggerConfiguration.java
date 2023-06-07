package com.rwc.randomwordchat.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        log.info("swagger page 접속");
        Info info = new Info()
                .title("Random Word Chat API Document")
                .version("v0.0.1")
                .description("랜덤 단어 채팅 API 문서");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
