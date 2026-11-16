package org.sopt.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String JWT_SCHEME = "JWT";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(JWT_SCHEME, bearerScheme))
                .addSecurityItem(new SecurityRequirement().addList(JWT_SCHEME))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("SOPT API") // API의 제목
                .description("PLAY SOPT 세미나를 위한 API") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}
