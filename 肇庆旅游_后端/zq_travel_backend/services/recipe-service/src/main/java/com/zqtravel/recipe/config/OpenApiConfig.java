package com.zqtravel.recipe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger配置类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI recipeServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("食谱服务API文档")
                        .description("肇庆旅游小程序 - 食谱服务API接口文档")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("肇庆旅游开发团队")
                                .email("dev@zqtravel.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8004")
                                .description("开发环境"),
                        new Server()
                                .url("http://api.zqtravel.com")
                                .description("生产环境")));
    }
}